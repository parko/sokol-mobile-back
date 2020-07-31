package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Details;
import com.sokolmeteo.dto.WeatherData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public ResponseEntity<Long> sendData(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                WeatherData data = processMessages(file);
                return new ResponseEntity<>(0L, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(1L, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> getState(String dataId) {
        return null;
    }

    @Override
    public ResponseEntity<Details> getDetails(String dataId) {
        return null;
    }

    private WeatherData processMessages(MultipartFile file) throws IOException {
        WeatherData data = new WeatherData();
        try (InputStream inputStream = file.getInputStream()){
            List<String> lines =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines().collect(Collectors.toList());
            data.setLoginMessage(lines.get(0));
            List<String> payloads = new ArrayList<>();
            String concatenated = lines.get(1);
            for (String payload : lines.subList(2, lines.size())) {
                if (concatenated.length() > 3500) {
                    payloads.add(concatenated);
                    concatenated = lines.get(1);
                }
                concatenated = concatenated.concat(payload);
            }
            payloads.add(concatenated);
            data.setBlackMessages(payloads);
        }
        return data;
    }
}
