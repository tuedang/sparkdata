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
        // Reference: https://spark.apache.org/docs/latest/configuration.html
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(masterUri);
        conf.set("es.resource", "vnf/companies");
        conf.set("es.nodes", "192.168.1.117");
        conf.set("spark.es.net.ssl", "false");
        conf.set("es.nodes.wan.only", "true");

        conf.set("spark.driver.memory", "4g");
        conf.set("spark.executor.memory", "4g");

//        conf.set("es.batch.size.bytes", "300000000");
//        conf.set("es.batch.size.entries", "5000");
//        conf.set("es.batch.write.refresh", "false");
//        conf.set("es.batch.write.retry.count", "50");
//        conf.set("es.batch.write.retry.wait", "20s");
//        conf.set("es.http.timeout", "50m");
//        conf.set("es.http.retries", "10");
//        conf.set("es.action.heart.beat.lead", "50");
//        conf.set("es.batch.size.entries", "10s");
//        conf.set("es.nodes.discovery", "false");
//        conf.set("es.nodes.client.only", "false");
//        conf.set("spark.executor.cores", "4");
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
