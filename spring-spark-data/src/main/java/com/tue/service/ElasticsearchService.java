package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.elasticsearch.hadoop.rest.query.FilteredQueryBuilder;
import org.elasticsearch.hadoop.rest.query.MatchAllQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ElasticsearchService {
    @Autowired
    private JavaSparkContext sc;

    public String handleES(String query) {
        QueryBuilder esquery = MatchAllQueryBuilder.MATCH_ALL;
        FilteredQueryBuilder filteredQueryBuilder = new FilteredQueryBuilder();
        filteredQueryBuilder.query(esquery);

        String q = String.format("?q=name:%s", query);
        JavaPairRDD<String, Map<String, Object>> esRDD = JavaEsSpark.esRDD(sc, "vnf/companies", q);

//        SparkSession sparkSession = new SparkSession(sc.sc());
//        Dataset<Row> df2 = sparkSession
//                .createDataset(esRDDVtown.collect(), Encoders.tuple(Encoders.STRING(), Encoders.STRING()))
//                .toDF("key", "value");
//        df2.show();

        return String.format("{\"count\": \"%s-%s\"}", 1, esRDD.count());
    }

//    private void queryBuilder() {
//        QueryBuilder query = MatchAllQueryBuilder.MATCH_ALL;
//        List<String> fields = new ArrayList<>();
//        fields.add("field1");
//        (new FilteredQueryBuilder().query(query).fields(fields).toString());
//        JavaPairRDD<String, Map<String, Object>> esRDD = JavaEsSpark.esRDD(sc, "index/type",  );
//        System.out.println(esRDD.take(1));
//    }
}
