package com.sokolmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataResponse<T> extends BaseResponse {
    @JsonProperty("result")
    private T result;

    public DataResponse(T result) {
        this.setState("OK");
        this.result = result;
    }
}
