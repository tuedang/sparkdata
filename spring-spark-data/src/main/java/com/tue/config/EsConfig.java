package com.tue.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tue.company")
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(HttpHost.create(esHost)));
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(@Autowired RestHighLevelClient client, @Autowired EntityMapper entityMapper) {
        return new ElasticsearchRestTemplate(client, entityMapper);
    }

    @Bean
    public EntityMapper entityMapper() {
        ElasticsearchEntityMapper elasticsearchEntityMapper = new ElasticsearchEntityMapper(new SimpleElasticsearchMappingContext(), new DefaultFormattingConversionService());
//        DefaultEntityMapper entityMapper = new DefaultEntityMapper(new SimpleElasticsearchMappingContext());

        //hacking objectMapper
//        Field field = ReflectionUtils.findField(DefaultEntityMapper.class, "objectMapper");
//        ReflectionUtils.makeAccessible(field);
//        ObjectMapper objectMapper = (ObjectMapper)ReflectionUtils.getField(field, entityMapper);
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//        ReflectionUtils.setField(field, entityMapper, objectMapper);
        return elasticsearchEntityMapper;
    }

}
