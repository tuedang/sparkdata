package com.tue.service;

import com.google.common.collect.Lists;
import com.tue.company.CompanyRepository;
import com.tue.company.PageableCompany;
import com.tue.company.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.elasticsearch.index.query.QueryBuilders.existsQuery;

@Slf4j
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Company getCompany(String id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getErrorCompanies() {
        return fetchData(existsQuery("_geodirectory.data"));
    }

    public List<Company> getMigrationCompanies() {
        return fetchData(existsQuery("_geodirectory.post_id"));
    }

    public List<Company> fetchData(QueryBuilder queryBuilder) {
        final List<Company> accumulatorCompany = Lists.newArrayList();
        fetchAllWithScroll(queryBuilder, 1000, companies -> accumulatorCompany.addAll(companies.getContent()));
        return accumulatorCompany;
    }

    public void fetchAllWithScroll(QueryBuilder queryBuilder, int size, @Nullable Consumer<PageableCompany> companyConsumer) {
        StopWatch stopWatch = StopWatch.createStarted();
        long scrollTime = TimeValue.timeValueMinutes(1L).millis();
        AtomicInteger atPage = new AtomicInteger();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(atPage.getAndIncrement(), size))
                .withQuery(queryBuilder)
                .build();

        Page<Company> companyPage = elasticsearchRestTemplate.startScroll(scrollTime, searchQuery, Company.class);
        notifyPageConsumer(companyConsumer, companyPage, size, atPage.get());

        while (companyPage.hasContent()) {
            String scrollId = ((AggregatedPage) companyPage).getScrollId();
            companyPage = elasticsearchRestTemplate.continueScroll(scrollId, scrollTime, Company.class);
            notifyPageConsumer(companyConsumer, companyPage, size, atPage.getAndIncrement());
        }
        String scrollId = ((AggregatedPage) companyPage).getScrollId();
        elasticsearchRestTemplate.clearScroll(scrollId);
        stopWatch.stop();
        log.info("Total fetch time: {}", stopWatch.toString());
    }

    private void notifyPageConsumer(@Nullable Consumer<PageableCompany> companyConsumer, Page<Company> companyPage, int size, int pageNumber) {
        if (companyConsumer == null) {
            return;
        }
        companyConsumer.accept(new PageableCompany(companyPage.getContent(), PageRequest.of(pageNumber, size), companyPage.getTotalElements()));
    }
}
