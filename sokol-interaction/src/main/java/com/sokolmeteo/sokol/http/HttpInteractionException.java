package com.sokolmeteo.sokol.http;

import lombok.Getter;

@Getter
public class HttpInteractionException extends RuntimeException {
    private final String localMessage;

    public HttpInteractionException(String message, String localMessage) {
        super(message);
        this.localMessage = localMessage;
    }
}
