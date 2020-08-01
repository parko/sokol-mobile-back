package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Log;
import com.sokolmeteo.dao.model.WeatherData;
import com.sokolmeteo.dao.repo.LogDao;
import com.sokolmeteo.sokol.tcp.TcpInteraction;
import com.sokolmeteo.sokol.tcp.TcpInteractionException;
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
    private final TcpInteraction tcpInteraction;
    private final LogDao logDao;

    public DataServiceImpl(TcpInteraction tcpInteraction, LogDao logDao) {
        this.tcpInteraction = tcpInteraction;
        this.logDao = logDao;
    }

    @Override
    public ResponseEntity<Long> sendData(MultipartFile file, String author) {
        Log log = new Log(author);
        log = logDao.save(log);
        if (!file.isEmpty()) {
            String details = null;
            try {
                WeatherData data = processMessages(file);
                for (String message : data.getBlackMessages()) {
                    details = message;
                    tcpInteraction.sendData(data.getLoginMessage(), message);
                }
                log.success();
                logDao.save(log);
                return new ResponseEntity<>(log.getId(), HttpStatus.OK);
            } catch (IOException e) {
                log.fault("Bad file", null);
                logDao.save(log);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (TcpInteractionException e) {
                e.printStackTrace();
                log.fault("Exception on sending message", details);
                logDao.save(log);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        log.fault("File is empty", null);
        logDao.save(log);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Log> getState(Long dataId, String author) {
        Log log = logDao.findByIdAndAuthor(dataId, author);
        return log != null ? new ResponseEntity<>(log, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
