package com.sokolmeteo.utils.exception;

public class NoDevicePermissionException extends RuntimeException {
    public NoDevicePermissionException(String message) {
        super(message);
    }
}
