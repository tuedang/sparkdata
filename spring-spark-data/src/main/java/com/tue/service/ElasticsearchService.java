package com.tue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ElasticsearchService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private SparkSession sparkSession;

    public String handleES(String query) {
        Dataset<Company> companyDataset = queryForCompany(sc, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website:*")
                        .withTerm("name", "tnhh")
                        .build());
        companyDataset.show();

        Dataset<Company> companyDatasetVtown = queryForCompany(sc, "vtown*/companies",
                CompanyQuery.builder()
                        .withQuery("website:*")
                        .build());
        companyDatasetVtown.show();

        Dataset<Row> companyJoined = companyDataset.join(companyDatasetVtown,
                companyDataset.col("website").equalTo(companyDatasetVtown.col("website")));
        companyJoined
                .select(companyDataset.col("name"),
                        companyDatasetVtown.col("name"),
                        companyDataset.col("website"))
                .show(false);

        return String.format("{\"count\": \"%s-%s => %s\"}", companyDataset.count(), companyDatasetVtown.count(), companyJoined.count());
    }

    private Dataset<Company> queryForCompany(JavaSparkContext javaSparkContext, String resource, QueryBuilder queryBuilder) {
        JavaPairRDD<String, Company> esCompanyRdd = JavaEsSpark.esJsonRDD(javaSparkContext, resource, queryBuilder.toString())
                .mapValues(objectMap -> OBJECT_MAPPER.readValue(objectMap, Company.class));
        JavaRDD<Company> companyRDD = esCompanyRdd.map(x -> x._2);
        return sparkSession.createDataset(companyRDD.rdd(), Encoders.bean(Company.class));
    }
}
