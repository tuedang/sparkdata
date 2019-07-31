package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ElasticsearchService {
    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private SparkSession sparkSession;

    public String handleES(String query) {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(sc, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTerm("name", "global")
                        .build(), Company.class);
        companyDataset.show();

        Dataset<Company> companyDatasetVtown = ElasticQueryHelper.queryForDataSet(sc, sparkSession, "vtown*/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTerm("name", "global")
                        .build(), Company.class);
        companyDatasetVtown.show();

        Dataset<Row> companyJoined = companyDataset.join(companyDatasetVtown,
                companyDataset.col("website").equalTo(companyDatasetVtown.col("website")));
        companyJoined
                .select(companyDataset.col("name"),
                        companyDataset.col("address.province"),
                        companyDatasetVtown.col("name"),
                        companyDataset.col("website"))
                .show(1000, false);

        return String.format("{\"count\": \"%s-%s => %s\"}", companyDataset.count(), companyDatasetVtown.count(), companyJoined.count());
    }
}
