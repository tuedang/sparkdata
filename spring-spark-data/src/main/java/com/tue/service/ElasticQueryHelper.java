package com.tue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;

public final class ElasticQueryHelper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> Dataset<T> queryForDataSet(JavaSparkContext javaSparkContext, SparkSession sparkSession,
                                                 String resource, QueryBuilder queryBuilder, Class<T> clazz) {
        JavaRDD<T> companyRDD = queryForRDD(javaSparkContext, resource, queryBuilder, clazz);
        return sparkSession.createDataset(companyRDD.rdd(), Encoders.bean(clazz));
    }

    public static <T> JavaRDD<T> queryForRDD(JavaSparkContext javaSparkContext,
                                             String resource, QueryBuilder queryBuilder, Class<T> clazz) {
        JavaPairRDD<String, T> esRdd = JavaEsSpark.esJsonRDD(javaSparkContext, resource, queryBuilder.toString())
                .mapValues(objectMap -> OBJECT_MAPPER.readValue(objectMap, clazz));
        return esRdd.map(x -> x._2);
    }

    public static <T> void showData(JavaRDD<T> javaRDD, SparkSession sparkSession, Class<T> clazz) {
        sparkSession.createDataset(javaRDD.rdd(), Encoders.bean(clazz))
                .show(50, false);
    }
}
