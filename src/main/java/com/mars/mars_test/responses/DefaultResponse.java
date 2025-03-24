package com.mars.mars_test.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mars.mars_test.enums.DefaultStatusEnum;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponse<T> {

    @JsonProperty
    private T data;

    @JsonProperty
    private DefaultStatusEnum status;

    @JsonProperty
    private String message;

    public DefaultResponse<T> success() {
        this.status = DefaultStatusEnum.SUCCESS;
        this.message = "Successfully.";
        return this;
    }
    public DefaultResponse<T> success(String message) {
        this.status = DefaultStatusEnum.SUCCESS;
        this.message = message;
        return this;
    }

    public DefaultResponse<T> error() {
        this.status = DefaultStatusEnum.FAILED;
        this.message = "Something went wrong.";
        return this;
    }

    public DefaultResponse<T> error(String message) {
        this.status = DefaultStatusEnum.FAILED;
        this.message = message;
        return this;
    }
}