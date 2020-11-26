package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.service.DataApiService;
import com.sokolmeteo.model.entity.Record;
import com.sokolmeteo.model.entity.WeatherData;
import com.sokolmeteo.model.service.RecordService;
import com.sokolmeteo.sokol.http.HttpInteractionException;
import com.sokolmeteo.sokol.http.service.AuthService;
import com.sokolmeteo.sokol.http.model.Login;
import com.sokolmeteo.sokol.http.service.DeviceService;
import com.sokolmeteo.sokol.tcp.TcpInteraction;
import com.sokolmeteo.utils.exception.FileParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class DataApiServiceImpl implements DataApiService {
    private static final Logger logger = LoggerFactory.getLogger(DataApiServiceImpl.class);

    private final AuthService authService;
    private final DeviceService deviceService;
    private final TcpInteraction tcpInteraction;
    private final RecordService recordService;

    public DataApiServiceImpl(AuthService authService, DeviceService deviceService, TcpInteraction tcpInteraction, RecordService recordService) {
        this.authService = authService;
        this.deviceService = deviceService;
        this.tcpInteraction = tcpInteraction;
        this.recordService = recordService;
    }

    @Override
    public void checkCredentials(String credentials) {
        authService.login(parseCredentials(credentials));
    }

    @Override
    public Long uploadFile(MultipartFile file, String credentials, String station, Long startDate, Long endDate) {
        Login login = parseCredentials(credentials);
        String sessionId = authService.login(login);
        WeatherData data = parse(file, station, startDate, endDate);
        deviceService.checkPermission(sessionId, data.getDeviceImei());
        data.setAuthor(login.getLogin());
        logger.info("Received weather data from " + login.getLogin());
        return tcpInteraction.send(data);
    }

    @Override
    public Record getState(String credentials, Long dataId) {
        Login login = parseCredentials(credentials);
        authService.login(login);
        return recordService.get(dataId, login.getLogin());
    }

    @Override
    public List<Record> getStates(String credentials, Integer page, Integer count) {
        Login login = parseCredentials(credentials);
        authService.login(login);
        return recordService.getAll(login.getLogin(), page, count);
    }

    private WeatherData parse(MultipartFile file, String station, Long start, Long end) {
        try (InputStream inputStream = file.getInputStream()) {
            WeatherData data = new WeatherData(station, start, end);
            List<String> lines =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines().collect(Collectors.toList());
            data.setLoginMessage(lines.get(0) + "\r\n");
            List<String> payloads = new ArrayList<>();
            StringBuilder concatenated = new StringBuilder(lines.get(1));
            for (String payload : lines.subList(2, lines.size())) {
                if (concatenated.length() > 3500) {
                    concatenated.append("\r\n");
                    payloads.add(concatenated.toString());
                    concatenated = new StringBuilder(lines.get(1));
                }
                concatenated.append(payload);
            }
            payloads.add(concatenated.append("\r\n").toString());
            data.setBlackMessages(payloads);
            return data;
        } catch (IOException e) {
            logger.warn("Exception on reading file: " + e);
            throw new FileParseException("Не удалось прочитать файл");
        }
    }

    private Login parseCredentials(String credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(credentials));
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            return (new Login(tokenizer.nextToken(), tokenizer.nextToken()));
        } catch (Exception e) {
            throw new HttpInteractionException("Unreadable credential string", "Нечитаемая строка логина и пароля");
        }
    }
}
