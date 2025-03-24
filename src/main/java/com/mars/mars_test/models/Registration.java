package com.mars.mars_test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mars.mars_test.enums.RegistrationStatusEnum;
import lombok.Data;

@Data
public class Registration {
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String address;

    @JsonProperty
    private String phoneNumber;

    @JsonProperty
    private String photoPath;

    @JsonProperty
    private String photoUrl;

    @JsonProperty
    private RegistrationStatusEnum status;
}
