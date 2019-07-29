package com.tue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.hadoop.rest.query.BoolQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryStringQueryBuilder;
import org.elasticsearch.hadoop.rest.query.TermQueryBuilder;
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
        Dataset<Company> companyDataset = queryForCompany(sc, "vnf/companies", queryBuilder());
        companyDataset.show();

        Dataset<Company> companyDatasetVtown = queryForCompany(sc, "vtown*/companies", queryBuilderVtown());
        companyDatasetVtown.show();

        return String.format("{\"count\": \"%s-%s\"}", companyDataset.count(), companyDatasetVtown.count());
    }

    private Dataset<Company> queryForCompany(JavaSparkContext javaSparkContext, String resource, QueryBuilder queryBuilder) {
        JavaPairRDD<String, Company> esCompanyRdd = JavaEsSpark.esJsonRDD(javaSparkContext, resource, queryBuilder.toString())
                .mapValues(objectMap -> OBJECT_MAPPER.readValue(objectMap, Company.class));
        JavaRDD<Company> companyRDD = esCompanyRdd.map(x -> x._2);
        return sparkSession.createDataset(companyRDD.rdd(), Encoders.bean(Company.class));
    }

    public QueryBuilder queryBuilder() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(new TermQueryBuilder()
                .field("name")
                .term("tnhh"));
        boolQueryBuilder.filter(new QueryStringQueryBuilder()
                .query("website:*"));

        log.info("QUERY: " + boolQueryBuilder);
        return boolQueryBuilder;
    }

    public QueryBuilder queryBuilderVtown() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(new QueryStringQueryBuilder()
                .query("website:*"));

        log.info("QUERY: " + boolQueryBuilder);
        return boolQueryBuilder;
    }
}
