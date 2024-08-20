package org.nihal.clients;

import org.nihal.config.ConnectionParams;

import java.io.IOException;

public interface ClientInterface {
    void connect(ConnectionParams params);
    String getDocumentById(String indexName, String id) throws IOException;
    String sql(String query)throws IOException;
}
