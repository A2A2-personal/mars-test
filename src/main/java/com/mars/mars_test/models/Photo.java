package com.mars.mars_test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Photo {
    @JsonProperty
    private String photoPath;

    @JsonProperty
    private String photoUrl;
}
