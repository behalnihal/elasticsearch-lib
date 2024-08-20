package org.nihal.opensearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConnectionParams;
import org.opensearch.client.opensearch.OpenSearchClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class OpensearchClient implements ClientInterface {
    private OpenSearchClient client;
    @Override
    public void connect(ConnectionParams params) {
        System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

    }

    @Override
    public String getDocumentById(String indexName, String id) throws IOException {
        return "";
    }

    @Override
    public JsonNode sql(String query) throws IOException {
        return null;
    }

    @Override
    public List<ObjectNode> searchDocuments(String indexName, String searchTerm, String field) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public boolean searchIndex(String indexName) throws IOException {
        return false;
    }

    @Override
    public List<ObjectNode> getDocumentsByDateRange(String indexName, String dateField, String startDate, String endDate, String dateFormat) throws IOException {
        return Collections.emptyList();
    }
}
