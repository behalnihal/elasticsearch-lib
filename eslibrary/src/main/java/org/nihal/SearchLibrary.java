package org.nihal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.clients.ClientFactory;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConfigurationLoader;
import org.nihal.config.ConnectionParams;

import java.io.IOException;
import java.util.List;

public class SearchLibrary {
    private ClientInterface client;
    public SearchLibrary(String clientType) throws IOException {
        ConnectionParams params = ConfigurationLoader.loadFromProperties();
        this.client = ClientFactory.getClient(clientType, params);
    }
    public String getDocumentById(String index, String id) throws IOException{
        return client.getDocumentById(index, id);
    }
    public JsonNode sql(String query) throws IOException {
        return client.sql(query);
    }
    public List<ObjectNode> searchDocument(String indexName, String searchTerm, String field) throws IOException{
        return client.searchDocuments(indexName, searchTerm, field);
    }
    public boolean searchIndex(String indexName) throws IOException{
        return client.searchIndex(indexName);
    }
    List<ObjectNode> getDocumentsByDateRange(String indexName, String dateField, String startDate, String endDate, String dateFormat) throws IOException{
        return client.getDocumentsByDateRange(indexName, dateField, startDate, endDate, dateFormat);
    }

}
