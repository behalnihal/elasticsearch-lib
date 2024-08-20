package org.nihal.opensearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConnectionParams;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;

import java.io.IOException;

public class OpensearchClient implements ClientInterface {
    private OpenSearchClient client;
    private ObjectMapper objectMapper;
    public OpensearchClient(){
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public void connect(ConnectionParams params) {
        System.setProperty("javax.net.ssl.trustStore", params.getTrustStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", params.getTrustStorePassword());

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
        GetRequest getRequest = new GetRequest.Builder()
                .index(indexName)
                .id(id)
                .build();

        GetResponse<ObjectNode> response = client.get(getRequest, ObjectNode.class);

        if (response.found()) {
            return response.source().toString();
        } else {
            return null;
        }
    }

    @Override
    public String sql(String query) throws IOException {
        String url = "https://localhost:9201/_sql";
        String params = "{\"query\": \"" + query + "\"}";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");

        StringEntity entity = new StringEntity(params);
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);
            return responseString;
        }else{
            throw new IOException();
        }
    }
}
