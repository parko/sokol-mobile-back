package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataService {
    Long sendData(MultipartFile file, String credentials, String station, Long startDate, Long endDate);

    Record getState(Long dataId, String author);

    List<Record> getStates(Integer page, Integer count, String author);
}
