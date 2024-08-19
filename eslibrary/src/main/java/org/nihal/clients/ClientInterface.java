package org.nihal.clients;

import com.fasterxml.jackson.databind.JsonNode;
import org.nihal.config.ConnectionParams;

import java.io.IOException;

public interface ClientInterface {
    void connect(ConnectionParams params);
    String getDocumentById(String indexName, String id) throws IOException;
    JsonNode sql(String query)throws IOException;
}
