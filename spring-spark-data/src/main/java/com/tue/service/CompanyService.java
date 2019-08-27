package com.tue.service;

import com.tue.company.CompanyRepository;
import com.tue.company.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company getCompany(String id) {
        return companyRepository.findById(id).get();
    }

    public Page<Company> getCompanies(int page, int size) {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, size));
        return companies;
    }
}
