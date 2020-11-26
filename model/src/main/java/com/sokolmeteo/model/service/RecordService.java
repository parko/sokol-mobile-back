package com.sokolmeteo.model.service;

import com.sokolmeteo.model.entity.Record;

import java.util.List;

public interface RecordService {
    Record get(Long id, String login);

    List<Record> getAll(String login);

    List<Record> getAll(String login, int page, int count);
}
