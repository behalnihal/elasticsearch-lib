package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;

import java.io.IOException;

public class SearchIndex {
    public static boolean searchIndex(ElasticsearchClient client, String indexName) throws IOException {
        boolean indexExists = client.indices().exists(
                ExistsRequest.of(e -> e.index(indexName))
        ).value();
        return indexExists;
    }
}
