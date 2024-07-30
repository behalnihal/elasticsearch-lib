package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.ElasticsearchOperations;

import java.io.IOException;
import java.util.Map;

public class UpdateDocument {

    public static void fullUpdateDocument(ElasticsearchClient client, String indexName, String id, ObjectNode updateBody) throws IOException {
        boolean isExist = ElasticsearchOperations.searchIndex(client, indexName);
        if(!isExist){
            System.out.println("Index named " + indexName + "not found");
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

        UpdateResponse<ObjectNode> updateResponse = client.update(u -> u
                        .index(indexName)
                        .id(id)
                        .doc(updateBody),
                ObjectNode.class
        );

        if (updateResponse.result().name().equals("Updated")) {
            System.out.println("Document with ID " + id + " successfully updated in index " + indexName);
        } else {
            System.out.println("Failed to update document with ID " + id + " in index " + indexName + ". Result: " + updateResponse.result().name());
        }
    }

    public static void partialUpdateDocument(ElasticsearchClient client, String indexName, String id, Map<String, Object> updateFields) throws IOException {
        // First, check if the document exists
        GetResponse<ObjectNode> getResponse = client.get(g -> g
                        .index(indexName)
                        .id(id),
                ObjectNode.class
        );

        if (!getResponse.found()) {
            System.out.println("Document with ID " + id + " not found in index " + indexName);
            return;
        }

        UpdateResponse<ObjectNode> updateResponse = client.update(u -> u
                        .index(indexName)
                        .id(id)
                        .doc(updateFields),
                ObjectNode.class
        );

        if (updateResponse.result().name().equals("Updated")) {
            System.out.println("Document with ID " + id + " successfully updated in index " + indexName);
        } else {
            System.out.println("Failed to update document with ID " + id + " in index " + indexName + ". Result: " + updateResponse.result().name());
        }
    }


}