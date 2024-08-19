package org.nihal.opensearch;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConnectionParams;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;

import java.io.IOException;

public class OpensearchClient implements ClientInterface {
    private OpenSearchClient client;
    @Override
    public void connect(ConnectionParams params) {
        System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

        final HttpHost host = new HttpHost(params.getHost(), params.getPort());
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(params.getUsername(), params.getPassword()));

        //Initialize the client with SSL and TLS enabled
        final RestClient restClient = RestClient.builder(host).
                setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();

        final OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new OpenSearchClient(transport);
    }

    @Override
    public String getDocumentById(String indexName, String id) throws IOException {
        return "";
    }

    @Override
    public JsonNode sql(String query) throws IOException {
        return null;
    }
}
