package com.sokolmeteo.utils.exception;

import lombok.Getter;

@Getter
public class NoDataFoundException extends RuntimeException {
    private final String localMessage;

    public NoDataFoundException(String message, String localMessage) {
        super(message);
        this.localMessage = localMessage;
    }
}
