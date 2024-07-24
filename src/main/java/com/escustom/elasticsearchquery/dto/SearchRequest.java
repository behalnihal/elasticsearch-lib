package com.escustom.elasticsearchquery.dto;

import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchRequest extends org.elasticsearch.action.search.SearchRequest {
    private String index;
    private String field;
    private String value;

    public SearchRequest() {}

    public SearchRequest(String index, String field, String value) {
        this.index = index;
        this.field = field;
        this.value = value;
    }

    public SearchRequest(String index) {
    }

    // Getters and Setters
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public org.elasticsearch.action.search.SearchRequest source(SearchSourceBuilder sourceBuilder) {
        return null;
    }

    @Override
    public void setParentTask(String parentTaskNode, long parentTaskId) {
        super.setParentTask(parentTaskNode, parentTaskId);
    }
}
