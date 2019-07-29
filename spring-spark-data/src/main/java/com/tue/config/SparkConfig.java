package com.tue.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${spark.app.name}")
    private String appName;
    @Value("${spark.master}")
    private String masterUri;
    //@Value("${spark.home}")
    //private String sparkHome;

    @Bean
    public SparkConf conf() {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(masterUri);
        conf.set("es.resource", "vnf/companies");
        conf.set("es.nodes", "tnode.org");
        conf.set("spark.es.net.ssl", "false");
        conf.set("es.nodes.wan.only", "true");

        conf.set("spark.driver.memory", "4g");
        conf.set("spark.executor.memory", "4g");
        return conf;
    }

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(conf());
    }

    @Bean
    public SparkSession sparkSession(JavaSparkContext sc) {
        return new SparkSession(sc.sc());
    }

}
