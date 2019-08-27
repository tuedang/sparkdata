package com.tue.service;

import com.tue.company.CompanyRepository;
import com.tue.company.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public void getCompany() {
        Company company = companyRepository.findById("02774e64b4d6fc472ea77c7ade25d2abe609070b").get();
        log.info("company={}", company);
    }
}
