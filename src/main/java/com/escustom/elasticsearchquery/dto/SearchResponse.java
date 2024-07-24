package com.escustom.elasticsearchquery.dto;

import org.elasticsearch.search.SearchHit;

public class SearchResponse {
    private long totalHits;
    private SearchHit[] hits;

    public SearchResponse() {}

    public SearchResponse(long totalHits, SearchHit[] hits) {
        this.totalHits = totalHits;
        this.hits = hits;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
    }

    public SearchHit[] getHits() {
        return hits;
    }

    public void setHits(SearchHit[] hits) {
        this.hits = hits;
    }
}
