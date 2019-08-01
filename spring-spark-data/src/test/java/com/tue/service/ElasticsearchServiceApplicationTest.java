package com.tue.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ElasticsearchServiceApplicationTest {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    public void handleES() {
        String output = elasticsearchService.handleES("tax*");
        log.info(output);
    }

    @Test
    public void joinCondition() throws Exception {
        elasticsearchService.joinCondition();
    }

    @Test
    public void addressVerification() {
        elasticsearchService.addressVerification();
    }

}
