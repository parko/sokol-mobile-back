package com.sokolmeteo.sokol.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginResponse implements SokolResponse {
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("id")
    private String id;
}
