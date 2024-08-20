package org.nihal.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import co.elastic.clients.elasticsearch.sql.QueryResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpUtils;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.nihal.clients.ClientInterface;
import org.nihal.config.ConnectionParams;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticSearchClient implements ClientInterface {
    private ElasticsearchClient client;
    @Override
    public void connect(ConnectionParams params) {
        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(params.getUsername(), params.getPassword()));

        RestClient restClient = RestClient
                .builder(new HttpHost(params.getHost(), params.getPort(), params.getScheme()))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credsProv)
                )
                .build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }

    @Override
    public String getDocumentById(String indexName, String id) throws IOException {
        GetResponse<ObjectNode> response = client.get(g -> g
                        .index(indexName)
                        .id(id),
                ObjectNode.class
        );
        if(!response.found()){
            return null;
        }
        return response.source().toString();
    }

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public JsonNode sql(String query) throws IOException {
        QueryRequest request = new QueryRequest.Builder()
                .query(query)
                .format("json")
                .build();

        QueryResponse response = client.sql().query(request);
        String res = JsonpUtils.toJsonString(response, client._jsonpMapper());
        return mapper.readTree(res);
    }

    @Override
    public List<ObjectNode> searchDocuments(String indexName, String searchTerm, String field) throws IOException {
        SearchResponse<ObjectNode> response = client.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .match(m -> m
                                        .field(field)
                                        .query(searchTerm)
                                )
                        ),
                ObjectNode.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @Override
    public boolean searchIndex(String indexName) throws IOException {
        boolean indexExists = client.indices().exists(
                ExistsRequest.of(e -> e.index(indexName))
        ).value();
        return indexExists;
    }

    @Override
    public List<ObjectNode> getDocumentsByDateRange(String indexName, String dateField, String startDate, String endDate, String dateFormat) throws IOException {
        SearchResponse<ObjectNode> response = client.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .range(r -> r
                                        .field(dateField)
                                        .gte(JsonData.of(startDate))
                                        .lte(JsonData.of(endDate))
                                        .format(dateFormat)
                                )
                        ),
                ObjectNode.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
