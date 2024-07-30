package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;

import java.io.IOException;

public class DeleteIndex {
    public static void deleteIndex(ElasticsearchClient client, String indexName) throws IOException {
        DeleteIndexResponse deleteResponse = client.indices().delete(createIndexBuilder -> createIndexBuilder.index(indexName)
        );
    }
}
