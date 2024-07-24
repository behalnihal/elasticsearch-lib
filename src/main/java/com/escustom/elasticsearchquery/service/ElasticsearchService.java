package com.escustom.elasticsearchquery.service;

import com.escustom.elasticsearchquery.dto.SearchRequest;
import com.escustom.elasticsearchquery.dto.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class ElasticsearchService {

    private final RestHighLevelClient client;

    public ElasticsearchService(RestHighLevelClient client) {
        this.client = client;
    }

    public SearchResponse search(SearchRequest request) {
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery(request.getField(), request.getValue()));

            SearchRequest searchRequest = new SearchRequest(request.getIndex());
            searchRequest.source(sourceBuilder);

            org.elasticsearch.action.search.SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            return new SearchResponse(Objects.requireNonNull(response.getHits().getTotalHits()).value, response.getHits().getHits());
        } catch (IOException e) {
            throw new RuntimeException("Error executing search", e);
        }
    }
}
