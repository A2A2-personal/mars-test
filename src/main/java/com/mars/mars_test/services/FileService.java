package com.mars.mars_test.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.mars_test.models.File;
import com.mars.mars_test.models.Photo;
import com.mars.mars_test.responses.FileResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileService {
    @Autowired
    private RestHighLevelClient client;

    @Value("${mars.upload.path}")
    String uploadPath;

    @Value("${mars.upload.file_service_url}")
    String fileServiceUrl;

    ObjectMapper objectMapper = new ObjectMapper();

    public Photo onUploadFile(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(uploadPath);
        Files.createDirectories(uploadDir);

        String fileId = String.valueOf(UUID.randomUUID());
        String fileName = fileId + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "");
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return new Photo(filePath.toString(), fileServiceUrl + fileName);
    }


    public FileResponse uploadFile(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(uploadPath);
        Files.createDirectories(uploadDir);

        String fileId = String.valueOf(UUID.randomUUID());
        String fileName = fileId + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "");
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        File photo = new File();
        photo.setFilePath(filePath.toString());
        photo.setFileId(fileId);
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setFileName(fileName);

        IndexRequest request = new IndexRequest("files")
                .source(objectMapper.writeValueAsString(photo), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
        return new FileResponse(fileId, fileServiceUrl + fileName);
    }

    public Resource readFile(String fileName) throws IOException {
//        TODO Validate file

//        SearchRequest searchRequest = new SearchRequest("files");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
//                .query(QueryBuilders.termQuery("fileId.keyword", fileId))
//                .size(100);
//        searchRequest.source(sourceBuilder);
//        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//        File file;
//        SearchHit[] hits = response.getHits().getHits();
//        if (hits.length > 0) {
//            Map<String, Object> result = hits[0].getSourceAsMap();
//            file = new ObjectMapper().convertValue(result, File.class);
//        } else {
//            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "File not found");
//        }
//        if (file == null) {
//            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "File not found");
//        }

        Path filePath = Paths.get(uploadPath + "/" + fileName);
        if (!Files.exists(filePath)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "File not found");
        }

        return new UrlResource(filePath.toUri());
    }
}
