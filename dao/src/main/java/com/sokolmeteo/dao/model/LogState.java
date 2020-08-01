package com.sokolmeteo.dao.model;

public enum LogState {
    IN_PROGRESS("В ПРОЦЕССЕ"),
    SENT("ОТПРАВЛЕНЫ"),
    FAULT("ОШИБКА");

    private final String name;

    LogState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
