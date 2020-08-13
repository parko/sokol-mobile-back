package com.sokolmeteo.dao.model;

public enum LogState {
    FAULT(0),
    IN_PROGRESS(1),
    SENT(2);

    private final int code;

    LogState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
