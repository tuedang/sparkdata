package com.tue.service;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.hadoop.rest.query.BoolQueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryBuilder;
import org.elasticsearch.hadoop.rest.query.QueryStringQueryBuilder;
import org.elasticsearch.hadoop.rest.query.RawQueryBuilder;
import org.elasticsearch.hadoop.rest.query.TermQueryBuilder;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@Slf4j
public final class CompanyQuery {
    private BoolQueryBuilder boolQueryBuilder;

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final JsonParser JSON_PARSER = new JsonParser();

    private CompanyQuery() {
        boolQueryBuilder = new BoolQueryBuilder();
    }

    public static CompanyQuery builder() {
        return new CompanyQuery();
    }

    public CompanyQuery withTerm(String term, String value) {
        boolQueryBuilder.must(new TermQueryBuilder()
                .field(term)
                .term(value));
        return this;
    }

    public CompanyQuery withTermKeyword(String field, String term) {
        boolQueryBuilder.must(new TermQueryBuilder()
                .field(field + ".keyword")
                .term(term));
        return this;
    }

    public CompanyQuery withQuery(String field, String queryString) {
        boolQueryBuilder.must(new QueryStringQueryBuilder()
                .query(field + ":" + queryString));
        return this;
    }

    public CompanyQuery withExactQuery(String field, String queryString) {
        boolQueryBuilder.must(new QueryStringQueryBuilder()
                .query(field + ":" + queryString));
        for (String keyword : StringUtils.split(queryString)) {
            boolQueryBuilder.must(new QueryStringQueryBuilder()
                    .query(field + ":" + keyword));
        }
        return this;
    }

    public CompanyQuery withMatchPhrase(String field, String queryString) {
        Map<String, Map> queryMap = ImmutableMap.of("match_phrase", ImmutableMap.of(field, queryString));
        return withQueryAsMap(queryMap);
    }

    public CompanyQuery withMatchPhrasePrefix(String field, String queryString) {
        Map<String, Map> queryMap = ImmutableMap.of("match_phrase_prefix", ImmutableMap.of(field, queryString));
        return withQueryAsMap(queryMap);
    }

    public CompanyQuery withQueryAsMap(Map<String, Map> queryMap) {
        try {
            boolQueryBuilder.must(new RawQueryBuilder(new JSONObject(queryMap).toJSONString(), false));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return this;
    }

    private String formatJson(String rawJson) {
        return GSON.toJson(JSON_PARSER.parse(rawJson));
    }

    public QueryBuilder build() {
        log.info("QUERY: " + formatJson(boolQueryBuilder.toString()));
        return boolQueryBuilder;
    }
}
