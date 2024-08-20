package org.nihal.opensearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConnectionParams;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpensearchClient implements ClientInterface {
    RestHighLevelClient client;
    @Override
    public void connect(ConnectionParams params) {
        System.setProperty("javax.net.ssl.trustStore", params.getTrustStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", params.getTrustStorePassword());

        //Establish credentials to use basic authentication.
        //Don't specify your credentials in code.
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(params.getUsername(), params.getPassword()));

        //Create a client.
        RestClientBuilder builder = RestClient.builder(new HttpHost(params.getHost(), params.getPort(), params.getScheme()))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        client = new RestHighLevelClient(builder);
    }

    @Override
    public String getDocumentById(String indexName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        return response.getSourceAsString();
    }

    @Override
    public JsonNode sql(String query) throws IOException {
        return null;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<ObjectNode> searchDocuments(String indexName, String searchTerm, String field) throws IOException {
        List<ObjectNode> results = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, searchTerm));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        hits.forEach(hit -> {
            try {
                ObjectNode node = (ObjectNode) objectMapper.readTree(hit.getSourceAsString());
                results.add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return results;
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
