package com.sokolmeteo.utils.exception;

import lombok.Getter;

@Getter
public class NoDevicePermissionException extends RuntimeException {
    private final String localMessage;

    public NoDevicePermissionException(String message, String localMessage) {
        super(message);
        this.localMessage = localMessage;
    }
}
