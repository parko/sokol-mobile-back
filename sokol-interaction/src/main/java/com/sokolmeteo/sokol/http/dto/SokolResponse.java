package com.sokolmeteo.sokol.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SokolResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;

    public SokolResponse(String code) {
        this.code = code;
    }
}
