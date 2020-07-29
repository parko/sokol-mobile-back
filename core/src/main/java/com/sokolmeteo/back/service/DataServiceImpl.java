package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public ResponseEntity<Long> sendData(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<String> getState(String dataId) {
        return null;
    }

    @Override
    public ResponseEntity<Details> getDetails(String dataId) {
        return null;
    }
}
