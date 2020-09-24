package com.sokolmeteo.dao.model;

public enum RecordState {
    FAULT("FAULT"),
    IN_PROGRESS("IN_PROGRESS"),
    SENT("SENT");

    private final String state;

    RecordState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
