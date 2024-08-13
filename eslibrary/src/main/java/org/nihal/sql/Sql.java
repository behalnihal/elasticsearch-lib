package org.nihal.sql;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import co.elastic.clients.elasticsearch.sql.QueryResponse;

import java.io.IOException;

public class Sql {
    public static String SqlOperation(ElasticsearchClient client, String query) throws IOException {
        QueryRequest request = new QueryRequest.Builder()
                .query(query)
                .format("json")
                .build();

        QueryResponse response = client.sql().query(request);

        return response.toString();
    }
}
