package com.tue.service;

import com.tue.company.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    public void findOneCompany() {
        Company company = companyService.getCompany("02774e64b4d6fc472ea77c7ade25d2abe609070b");
        log.info("company={}", company);
    }

    @Test
    public void getCompanyWithScroll() {
        AtomicInteger atomicInteger = new AtomicInteger();
        QueryBuilder queryBuilder = matchAllQuery();// existsQuery("website");// matchQuery("website", "\\*");
        companyService.fetchAllWithScroll(queryBuilder, 1000, companyPage -> {
            log.info("page={}", companyPage);
            companyPage.forEach(company -> log.info("{}. Company={}-{}", atomicInteger.incrementAndGet(), company.getId(), company.getName()));
        });
    }

    @Test
    public void getErrorCompanies() {
        List<Company> companies = companyService.getErrorCompanies();

        companies.forEach(company -> {
            log.info("error={}/{}/{}", company.getId(), company.getTaxId(), company.getGeoDirectory().toStringNonNull());
        });
        log.info("total={}", companies.size());
    }

    @Test
    public void getMigrationCompanies() {
        List<Company> companies = companyService.getMigrationCompanies();
        companies.forEach(company -> {
            log.info("Migration company={} / {}", company.getId(), company.getGeoDirectory().toStringNonNull());
        });
        log.info("total={}", companies.size());
    }

}
