package com.tue.service;

import org.elasticsearch.hadoop.rest.query.FilteredQueryBuilder;
import org.elasticsearch.hadoop.rest.query.MatchAllQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.hadoop.rest.query.TermQueryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EsQueryBuilderTest {
    @Test
    public void queryBuilder() {
        QueryBuilder query = MatchAllQueryBuilder.MATCH_ALL;

        TermQueryBuilder queryTerm = new TermQueryBuilder()
                .field("name")
                .term("tax*");
        String queryString = new FilteredQueryBuilder()
                .query(query)
                .query(queryTerm)
                .toString();
        System.out.println(queryString);
    }
}
