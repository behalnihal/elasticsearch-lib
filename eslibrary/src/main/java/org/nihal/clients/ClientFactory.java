package org.nihal.clients;


import org.nihal.config.ConnectionParams;
import org.nihal.elasticsearch.ElasticSearchClient;
import org.nihal.opensearch.OpensearchClient;

public class ClientFactory {
    public static ClientInterface getClient(String clientType, ConnectionParams params){
        ClientInterface client;
        if("elasticsearch".equalsIgnoreCase(clientType)) {
            client = new ElasticSearchClient();
        }
        else if("opensearch".equalsIgnoreCase(clientType)){
            client = new OpensearchClient();
        } else{
            throw new IllegalArgumentException("Invalid client type");
        }
        client.connect(params);
        return client;
    }
}
