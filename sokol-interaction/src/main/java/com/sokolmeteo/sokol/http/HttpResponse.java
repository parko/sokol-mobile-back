package com.sokolmeteo.sokol.http;

import lombok.Getter;

@Getter
public class HttpResponse {
    private Boolean success;
    private String code;
    private String message;
}
