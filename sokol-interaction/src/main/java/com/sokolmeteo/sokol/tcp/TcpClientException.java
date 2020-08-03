package com.sokolmeteo.sokol.tcp;

import lombok.Getter;

public class TcpClientException extends RuntimeException {
    @Getter
    private final String details;

    public TcpClientException(String message, String details) {
        super(message);
        this.details = details;
    }
}
