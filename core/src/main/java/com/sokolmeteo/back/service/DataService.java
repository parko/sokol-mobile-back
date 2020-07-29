package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DataService {
    ResponseEntity<Long> sendData(MultipartFile file);

    ResponseEntity<String> getState(String dataId);

    ResponseEntity<Details> getDetails(String dataId);
}
