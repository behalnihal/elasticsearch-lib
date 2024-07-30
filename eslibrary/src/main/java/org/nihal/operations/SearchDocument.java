package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SearchDocument {
    public static List<ObjectNode> searchDocuments(ElasticsearchClient client, String indexName, String searchTerm, String field) throws IOException {
        SearchResponse<ObjectNode> response = client.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .match(m -> m
                                        .field(field)
                                        .query(searchTerm)
                                )
                        ),
                ObjectNode.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}