package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DataService {
    ResponseEntity<Long> sendData(MultipartFile file, String author);

    ResponseEntity<Log> getState(Long dataId, String author);
}
