package com.escustom.elasticsearchquery.controller;
import com.escustom.elasticsearchquery.dto.SearchRequest;
import com.escustom.elasticsearchquery.dto.SearchResponse;
import com.escustom.elasticsearchquery.service.ElasticsearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final ElasticsearchService elasticsearchService;

    public SearchController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest request) {
        return elasticsearchService.search(request);
    }
}
