package com.mars.mars_test.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mars.mars_test.enums.RegistrationStatusEnum;
import lombok.Data;

@Data
public class UpdateStatusRegistrationRequest {
    @JsonProperty
    private RegistrationStatusEnum status;
}
