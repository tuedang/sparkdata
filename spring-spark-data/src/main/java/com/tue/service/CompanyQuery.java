package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.hadoop.rest.query.BoolQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryStringQueryBuilder;
import org.elasticsearch.hadoop.rest.query.TermQueryBuilder;

@Slf4j
public final class CompanyQuery {
    private BoolQueryBuilder boolQueryBuilder;

    private CompanyQuery() {
        boolQueryBuilder = new BoolQueryBuilder();
    }

    public static CompanyQuery builder() {
        return new CompanyQuery();
    }

    public CompanyQuery withTerm(String term, String value) {
        boolQueryBuilder.filter(new TermQueryBuilder()
                .field(term)
                .term(value));
        return this;
    }

    public CompanyQuery withQuery(String field, String queryString) {
        boolQueryBuilder.filter(new QueryStringQueryBuilder()
                .query(field + ":" + queryString));
        return this;
    }

    public QueryBuilder build() {
        log.info("QUERY: " + boolQueryBuilder);
        return boolQueryBuilder;
    }
}
