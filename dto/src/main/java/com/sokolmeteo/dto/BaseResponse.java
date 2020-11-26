package com.sokolmeteo.dto;

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
    @JsonProperty("message")
    @Getter @Setter
    private String message;
    @JsonProperty("localMessage")
    @Getter @Setter
    private String localMessage;
    @JsonProperty("timestamp")
    private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

    public BaseResponse() {
    }

    protected BaseResponse(String state) {
        this.state = state;
    }

    public BaseResponse(String state, List<String> errors) {
        this.state = state;
        this.errors = errors;
    }

    public BaseResponse(String state, String message, String localMessage) {
        this.state = state;
        this.message = message;
        this.localMessage = localMessage;
    }
}
