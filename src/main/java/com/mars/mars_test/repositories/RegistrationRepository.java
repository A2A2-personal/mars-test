package com.mars.mars_test.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.mars_test.enums.RegistrationStatusEnum;
import com.mars.mars_test.models.Registration;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.action.update.UpdateResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RegistrationRepository {
    private static final String INDEX = "registrations";

    @Autowired
    private RestHighLevelClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public IndexResponse save(Registration registration) throws IOException {
        IndexRequest request = new IndexRequest(INDEX)
                .id(registration.getId())
                .source(objectMapper.writeValueAsString(registration), XContentType.JSON);

        return client.index(request, RequestOptions.DEFAULT);
    }

    public Registration findById(String id) throws IOException {
        GetRequest request = new GetRequest(INDEX, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        if (response.isExists()) {
            Registration reg = objectMapper.convertValue(response.getSourceAsMap(), Registration.class);
            reg.setId(response.getId());
            return reg;
        }
        return null;
    }
    public DeleteResponse deleteById(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX, id);
        return client.delete(request, RequestOptions.DEFAULT);
    }

    public List<Registration> findAll() throws IOException {
//        DeleteIndexRequest request = new DeleteIndexRequest("registrations");
//        client.indices().delete(request, RequestOptions.DEFAULT);

        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .size(100);

        searchRequest.source(sourceBuilder);

        SearchResponse response;
        List<Registration> result = new ArrayList<>();
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            return result;
        }

        for (SearchHit hit : response.getHits().getHits()) {
            Registration reg = objectMapper.convertValue(hit.getSourceAsMap(), Registration.class);
            reg.setId(hit.getId());
            result.add(reg);
        }
        return result;
    }

    public UpdateResponse updateStatusById(String id, RegistrationStatusEnum status) throws IOException {
        Map<String, Object> updateMap = Map.of("status", status.name());
        UpdateRequest updateRequest = new UpdateRequest(INDEX, id)
                .doc(updateMap);
        return client.update(updateRequest, RequestOptions.DEFAULT);
    }

    public UpdateResponse updateById(String id, Map<String, Object> updateMap) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, id)
                .doc(updateMap);

        return client.update(updateRequest, RequestOptions.DEFAULT);
    }
}
