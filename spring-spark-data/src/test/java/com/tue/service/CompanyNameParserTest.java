package com.tue.service;

import com.tue.spark.naming.CompanyNameParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class CompanyNameParserTest {
    @Autowired
    private JavaSparkContext javaSparkContext;
    @Autowired
    private SparkSession sparkSession;

    private static final CompanyNameParser companyNameParser = new CompanyNameParser();

    @Test
    public void verifyParsingName() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withMatchPhrase("address.district", "quận tân bình")
                        .build(), Company.class);
        JavaRDD<String> javaRDD= companyDataset.javaRDD()
                .map(company -> companyNameParser.parse(company.getName()))
                .map(companyNameComponent -> companyNameComponent.getName());
        javaRDD.foreachAsync(s -> System.out.println(s));
        System.out.println(javaRDD.count());

    }
}
