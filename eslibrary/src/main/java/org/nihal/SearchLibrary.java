package org.nihal;

import com.fasterxml.jackson.databind.JsonNode;
import org.nihal.clients.ClientFactory;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConfigurationLoader;
import org.nihal.config.ConnectionParams;

import java.io.IOException;

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
}
