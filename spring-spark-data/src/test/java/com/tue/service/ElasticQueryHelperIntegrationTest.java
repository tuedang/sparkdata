package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ElasticQueryHelperIntegrationTest {
    @Autowired
    private JavaSparkContext javaSparkContext;
    @Autowired
    private SparkSession sparkSession;

    @Test
    public void queryForFilter() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTerm("name", "global")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(1)
                .allMatch(company -> StringUtils.containsIgnoreCase(company.getName(), "global"))
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForFilter_multipleTerm_asAndOperator() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTerm("name", "global")
                        .withTerm("name", "tnhh")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(1)
                .allMatch(company -> StringUtils.containsIgnoreCase(company.getName(), "global"))
                .allMatch(company -> StringUtils.containsIgnoreCase(company.getName(), "tnhh"))
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForFilter_multipleTermAsAndOperator_withDistrict() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withTermKeyword("address.district", "Quận Tân Bình")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(1)
                .allMatch(company -> StringUtils.equalsAnyIgnoreCase(company.getAddress().getDistrict(), "quận tân bình"))
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForFilter_matchPhrase_withDistrict() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withMatchPhrase("address.district", "quận tân bình")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(10)
                .allMatch(company -> StringUtils.equalsAnyIgnoreCase(company.getAddress().getDistrict(), "quận tân bình"))
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForFilter_matchPhrasePrefix_withDistrict() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withMatchPhrasePrefix("address.district", "quận tân b")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(10)
                .allMatch(company -> StringUtils.equalsAnyIgnoreCase(company.getAddress().getDistrict(), "quận tân bình"));
    }

    @Test
    public void queryForFilter_matchPhrasePrefix_withVtown() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vtown*/companies",
                CompanyQuery.builder()
                        .withMatchPhrase("address.address", "quận tân bình")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(10)
                .allMatch(company -> StringUtils.containsAny(company.getAddress().getAddress(), "quận Tân Bình"));

        Dataset<Company> companyBinhTanDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vtown*/companies",
                CompanyQuery.builder()
                        .withMatchPhrase("address.address", "quận bình tân")
                        .build(), Company.class);
        List<Company> companiesBinhTan = companyBinhTanDataset.collectAsList();
        assertThat(companiesBinhTan)
                .hasSizeGreaterThan(10)
                .allMatch(company -> StringUtils.containsAny(company.getAddress().getAddress(), "Quận Bình Tân"));
    }

    @Test
    public void queryForAddress_matchAddressProvince_withSpace() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withExactQuery("address.district", "quận 7")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(10)
                .as("Matching address#district as 'Quan 7'")
                .allMatch(company -> StringUtils.equalsAnyIgnoreCase(company.getAddress().getDistrict(), "quan 7", "quận 7"))
                .as("Matching website must have value")
                .allMatch(company -> StringUtils.isNotEmpty(company.getWebsite()));
    }

    @Test
    public void queryForAddress_matchProvince_HoChiMinh() {
        Dataset<Company> companyDataset = ElasticQueryHelper.queryForDataSet(javaSparkContext, sparkSession, "vnf/companies",
                CompanyQuery.builder()
                        .withQuery("website", "*")
                        .withExactQuery("address.province", "hồ chí minh")
                        .build(), Company.class);
        List<Company> companies = companyDataset.collectAsList();
        assertThat(companies)
                .hasSizeGreaterThan(10)
                .as("Matching address#province contains 'Hồ Chí Minh'")
                .allMatch(company -> StringUtils.equalsAnyIgnoreCase(company.getAddress().getProvince(), "TP Hồ Chí Minh", "TP Ho Chi Minh"));
    }

}
