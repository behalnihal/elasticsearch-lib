package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class GetDocumentById {
    private static final ObjectMapper objectMapper = new JacksonJsonpMapper().objectMapper();
    public static <T> T getDocumentById(ElasticsearchClient client, String indexName, String id, Class<T> documentClass) throws IOException {
        GetResponse<ObjectNode> response = client.get(g -> g
                        .index(indexName)
                        .id(id),
                ObjectNode.class
        );
        if(!response.found()){
            JsonNode emptyNode = objectMapper.createObjectNode();
            System.out.println("document with id " + id + " not found in index " + indexName);
            return objectMapper.convertValue(emptyNode, documentClass);
        }
        return objectMapper.convertValue(response.source(), documentClass);
    }
}
