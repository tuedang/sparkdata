package com.tue.service;

import com.tue.domain.similarity.StringSimilarity;
import com.tue.spark.address.AddressComponent;
import com.tue.spark.address.AddressParserDelegator;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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

    public void joinCondition() throws ExecutionException, InterruptedException {
        QueryBuilder companyQuery = CompanyQuery.builder()
                .withQuery("website", "*")
                .withTerm("name", "global")
                .build();
        JavaRDD<Company> companyRdd = ElasticQueryHelper.queryForRDD(sc, "vnf/companies", companyQuery, Company.class);
        JavaRDD<Company> companyRddVtown = ElasticQueryHelper.queryForRDD(sc, "vtown*/companies", companyQuery, Company.class);

        JavaPairRDD<Company, Company> joined = companyRdd.cartesian(companyRddVtown)
                .filter(tuple2 -> {
                    double confident = StringSimilarity.isSimilarAddress(tuple2._1.getAddress().getAddress(), tuple2._2.getAddress().getAddress());
                    //System.out.println(String.format("%s: [%s<-->%s]", confident, tuple2._1.getAddress().getAddress(), tuple2._2.getAddress().getAddress()));
                    return confident > 0.60;
                });
        joined.foreach(tuple2 -> {
            System.out.println(String.format("[%s<-->%s]", tuple2._1.getAddress().getAddress(), tuple2._2.getAddress().getAddress()));
        });
        ElasticQueryHelper.showData(joined.keys(), sparkSession, Company.class);
    }

    public void addressVerification() {
        QueryBuilder companyQuery = CompanyQuery.builder()
                .withQuery("website", "*")
//                .withTerm("name", "global")
                .build();

        JavaRDD<Company> companyRddVtown = ElasticQueryHelper.queryForRDD(sc, "vnf/companies", companyQuery, Company.class);
        companyRddVtown.collect().forEach(company -> {
            String rawAddress = company.getAddress().getAddress();
            if (rawAddress != null) {
                AddressComponent addressComponent = new AddressParserDelegator().parse(company.getAddress().getAddress());
                System.out.println(String.format("%s [%s]", addressComponent, company.getAddress().getAddress()));
            }
        });
    }
}
