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

    public static JsonNode getDocumentById(ElasticsearchClient client, String indexName, String id) throws IOException {
        return GetDocumentById.getDocumentById(client, indexName, id);
    }

    public static List<ObjectNode> searchDocument(ElasticsearchClient client, String indexName, String searchTerm, String field) throws IOException{
        return SearchDocument.searchDocuments(client, indexName, searchTerm, field);
    }

    public static List<ObjectNode> getDocumentsByDateRange(ElasticsearchClient client, String indexName, String dateField, String startDate, String endDate, String format) throws IOException {
        return DateRangeOperation.getDocumentsByDateRange(client, indexName, dateField, startDate, endDate, format);
    }
    
    public static boolean searchIndex(ElasticsearchClient client, String indexName) throws IOException{
        return SearchIndex.searchIndex(client, indexName);
    }

}
