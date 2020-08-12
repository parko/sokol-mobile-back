package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.AuthSession;
import com.sokolmeteo.dao.model.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataService {
    ResponseEntity<Long> sendData(MultipartFile file, AuthSession session);

    ResponseEntity<Log> getState(Long dataId, String author);

    ResponseEntity<List<Log>> getStates(Integer page, Integer count, String author);
}
