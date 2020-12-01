package com.sokolmeteo.sokol.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class LoginResponse extends SokolResponse {
    @Getter
    @JsonProperty("id")
    private String id;
}
