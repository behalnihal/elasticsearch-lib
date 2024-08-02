package org.nihal.operations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DateRangeOperation {
    public static List<ObjectNode> getDocumentsByDateRange(ElasticsearchClient client, String indexName, String dateField, String startDate, String endDate, String dateFormat) throws IOException {
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