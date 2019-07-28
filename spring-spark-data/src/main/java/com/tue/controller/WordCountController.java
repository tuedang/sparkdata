package com.tue.controller;

import com.tue.service.ElasticsearchService;
import com.tue.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class WordCountController {

    @Autowired
    private WordCountService service;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @RequestMapping(method = RequestMethod.POST, path = "/wordcount")
    public Map<String, Long> count(@RequestParam(required = false) String words) {
        List<String> wordList = Arrays.asList(words.split("\\|"));
        return service.getCount(wordList);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/es")
    public Object es(@RequestParam(required = false) String query) throws Exception {
        return elasticsearchService.handleES(query);
    }

}
