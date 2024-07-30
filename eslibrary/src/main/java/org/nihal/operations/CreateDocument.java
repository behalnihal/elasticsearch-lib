package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;

public class CreateDocument {
    public static <T> void createDocument(ElasticsearchClient client, String indexName, String id, T document) throws IOException {
        IndexResponse response = client.index(i -> i
                .index(indexName)
                .id(id)
                .document(document)
        );
    }
}
