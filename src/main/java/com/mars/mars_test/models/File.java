package com.mars.mars_test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class File {
    @JsonProperty
    private String fileId;

    @JsonProperty
    private String filePath;

    @JsonProperty
    private String fileName;

    @JsonProperty
    private String originalFileName;
}
