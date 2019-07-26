package com.tue.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

public final class SparkContext<T> {
    private static JavaSparkContext javaSparkContext;
    private static SparkSession sparkSession;
    private static SQLContext sqlContext;

    public static SparkConf conf() {
        SparkConf conf = new SparkConf()
                .setAppName("appname")
                .setMaster("local");
        return conf;
    }

    public static SparkSession sparkSession() {
        if (sparkSession == null)
            System.setProperty("hadoop.home.dir", "./spark-warehouse");
            sparkSession = SparkSession.builder()
                    .appName("appname")
                    .master("local")
                    .getOrCreate();
        return sparkSession;
    }

    public static JavaSparkContext javaSparkContext() {
        if (javaSparkContext == null)
            javaSparkContext = new JavaSparkContext();
        return javaSparkContext;
    }

    public static SQLContext sqlContext() {
        if (sqlContext == null)
            sqlContext = sparkSession().sqlContext();
        return sqlContext;
    }

}
