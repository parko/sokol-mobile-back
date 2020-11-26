package com.sokolmeteo.back.service;

import com.sokolmeteo.model.entity.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataApiService {
    void checkCredentials(String credentials);

    Long uploadFile(MultipartFile file, String credentials, String station, Long startDate, Long endDate);

    Record getState(String credentials, Long dataId);

    List<Record> getStates(String credentials, Integer page, Integer count);
}
