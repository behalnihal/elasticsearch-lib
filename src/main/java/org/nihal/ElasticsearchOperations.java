package org.nihal;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.config.ElasticsearchConfig;
import org.nihal.operations.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ElasticsearchOperations {

    public static ElasticsearchClient getClient(String username, String password) throws IOException {
        return ElasticsearchConfig.getElasticsearchClient(username, password);
    }

    public static void createIndex(ElasticsearchClient client, String indexName) throws IOException {
        client.indices().create(c -> c.index(indexName));
    }
    public static <T> void createDocument(ElasticsearchClient client, String indexName, String id, T document) throws IOException {
        CreateDocument.createDocument(client, indexName, id, document);
    }

    public static <T> T getDocumentById(ElasticsearchClient client, String indexName, String id, Class<T> documentClass) throws IOException {
        return GetDocumentById.getDocumentById(client, indexName, id, documentClass);
    }

    public static List<ObjectNode> searchDocument(ElasticsearchClient client, String indexName, String searchTerm, String field) throws IOException{
        return SearchDocument.searchDocuments(client, indexName, searchTerm, field);
    }

    public static List<ObjectNode> getDocumentsByDateRange(ElasticsearchClient client, String indexName, String dateField, long startTimestamp, long endTimestamp) throws IOException {
        return DateRangeOperation.getDocumentsByDateRange(client, indexName, dateField, startTimestamp, endTimestamp);
    }

    public static void DeleteDocument(ElasticsearchClient client, String indexName, String id) throws IOException {
        DeleteDocument.deleteDocument(client, indexName, id);
        return;
    }

    public static boolean searchIndex(ElasticsearchClient client, String indexName) throws IOException{
        return SearchIndex.searchIndex(client, indexName);
    }

    public static void updateDocument(ElasticsearchClient client, String indexName, String id, ObjectNode update) throws IOException {
        UpdateDocument.fullUpdateDocument(client, indexName, id, update);
        return;
    }

    public static void partialUpdateDocument(ElasticsearchClient client, String indexName, String id, Map<String, Object> updateFields) throws IOException {
        UpdateDocument.partialUpdateDocument(client, indexName, id, updateFields);
        return;
    }
}