package org.nihal.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nihal.config.ConnectionParams;

import java.io.IOException;
import java.util.List;

public interface ClientInterface {
    void connect(ConnectionParams params);
    String getDocumentById(String indexName, String id) throws IOException;
    JsonNode sql(String query)throws IOException;
    List<ObjectNode> searchDocuments(String indexName, String searchTerm, String field) throws IOException;
    boolean searchIndex(String indexName) throws IOException;
    List<ObjectNode> getDocumentsByDateRange(String indexName, String dateField, String startDate, String endDate, String dateFormat) throws IOException;
}
