package com.tue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.hadoop.rest.query.FilteredQueryBuilder;
import org.elasticsearch.hadoop.rest.query.MatchAllQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryStringQueryBuilder;
import org.elasticsearch.hadoop.rest.query.SimpleQueryParser;
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
        QueryBuilder esquery = MatchAllQueryBuilder.MATCH_ALL;
        FilteredQueryBuilder filteredQueryBuilder = new FilteredQueryBuilder();
        filteredQueryBuilder.query(esquery);

        String q = String.format("?q=name:%s", queryBuilder());
        JavaPairRDD<String, Company> esCompanyRdd = JavaEsSpark.esJsonRDD(sc, "vnf/companies", queryBuilder())
                .mapValues(objectMap -> OBJECT_MAPPER.readValue(objectMap, Company.class));
        JavaRDD<Company> companyRDD = esCompanyRdd.map(x -> x._2);

        Dataset<Company> companyDataset =  sparkSession.createDataset(companyRDD.rdd(), Encoders.bean(Company.class));
        companyDataset.show();

        return String.format("{\"count\": \"%s-%s\"}", 1, companyDataset.count());
    }

    public String queryBuilder() {
        QueryBuilder query = MatchAllQueryBuilder.MATCH_ALL;

        TermQueryBuilder queryTerm = new TermQueryBuilder()
                .field("tax_id")
                .term("0104553471");
        String queryString = new FilteredQueryBuilder()
//                .query(query)
                .query(queryTerm)
                .toString();

        return queryTerm.toString();
    }
}
