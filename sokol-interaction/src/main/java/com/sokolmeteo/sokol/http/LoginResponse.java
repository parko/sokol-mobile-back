package com.sokolmeteo.sokol.http;

import lombok.Getter;

@Getter
public class LoginResponse {
    private Boolean success;
    private String code;
    private String message;
}
