package com.mars.mars_test.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mars.mars_test.models.Registration;
import lombok.Data;

@Data
public class RegistrationRequest {
    @JsonProperty
    private String name;

    @JsonProperty
    private String address;

    @JsonProperty
    private String phoneNumber;

    @JsonIgnoreProperties
    public Registration getRegistration() {
        Registration registration = new Registration();
        registration.setName(name);
        registration.setAddress(address);
        registration.setPhoneNumber(phoneNumber);
        return registration;
    }
}
