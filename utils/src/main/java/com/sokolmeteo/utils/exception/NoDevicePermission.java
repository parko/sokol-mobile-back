package com.sokolmeteo.utils.exception;

public class NoDevicePermission extends RuntimeException {
    public NoDevicePermission(String message) {
        super(message);
    }
}
