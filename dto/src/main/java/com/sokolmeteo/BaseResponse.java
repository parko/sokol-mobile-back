package com.sokolmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

public class BaseResponse {
    @JsonProperty("state")
    @Getter @Setter
    private String state;
    @JsonProperty("errors")
    @Getter @Setter
    private List<String> errors;
    @JsonProperty("timestamp")
    private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

    public BaseResponse() {
    }

    public BaseResponse(String state) {
        this.state = state;
    }

    public BaseResponse(String state, List<String> errors) {
        this.state = state;
        this.errors = errors;
    }
}
