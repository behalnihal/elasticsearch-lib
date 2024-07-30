package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.ElasticsearchOperations;

import java.io.IOException;

public class DeleteDocument {
    public static void deleteDocument(ElasticsearchClient client, String indexName, String id) throws IOException {
        boolean indexExists = ElasticsearchOperations.searchIndex(client, indexName);
        if (!indexExists) {
            System.out.println("Index " + indexName + " does not exist");
            return;
        }
        GetResponse<ObjectNode> getResponse = client.get(g -> g
                        .index(indexName)
                        .id(id),
                ObjectNode.class
        );

        if (!getResponse.found()) {
            System.out.println("Document with ID " + id + " not found in index " + indexName);
            return;
        }

        DeleteResponse deleteResponse = client.delete(d -> d
                .index(indexName)
                .id(id)
        );

        if (deleteResponse.result().name().equals("Deleted")) {
            System.out.println( "Document with ID " + id + " successfully deleted from index " + indexName);
        } else {
            System.out.println("Failed to delete document with ID " + id + " from index " + indexName + ". Result: " + deleteResponse.result().name());
        }
    }
}