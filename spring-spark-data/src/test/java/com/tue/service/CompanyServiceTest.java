package com.tue.service;

import com.tue.company.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

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
    public void printMany() {
        AtomicInteger page = new AtomicInteger();
        Page<Company> companies;
        do {
            companies = companyService.getCompanies(page.getAndIncrement(), 100);
            companies.forEach(company -> log.info("{}.company={}", page, company));
            break;
        } while (!companies.isEmpty());
    }
}
