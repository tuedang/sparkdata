package com.tue.company;

import com.tue.company.model.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CompanyRepository extends ElasticsearchRepository<Company, String> {
    List<Company> findByName(String name);

}
