package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ElasticQueryHelperIntegrationTest {
    @Autowired
    private JavaSparkContext javaSparkContext;
    @Autowired
    private SparkSession sparkSession;

    @Test
    public void queryForFilter() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTerm("name", "global")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(1)
                .allMatch(company -> StringUtils.containsIgnoreCase(company.getName(), "global"))
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForAddress_matchAddress_withSpace() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
//                        .withTerm("address.address", "quan\\ 3")
//                        .withTerm("address.address", "3")
                        .withQuery("address.district", "'quan\\ 1'")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(1)
                .as("Matching address contains 'Quan 1'")
                .allMatch(company -> StringUtils.containsIgnoreCase(company.getAddress().getAddress(), "quan 1")
                        || StringUtils.containsIgnoreCase(company.getAddress().getAddress(), "quận 1")
                )
                .as("Matching website must have value")
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

}
