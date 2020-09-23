package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.AuthSession;
import com.sokolmeteo.dao.model.Device;
import com.sokolmeteo.dao.model.Record;
import com.sokolmeteo.dao.model.WeatherData;
import com.sokolmeteo.dao.repo.RecordDao;
import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.tcp.TcpInteraction;
import com.sokolmeteo.utils.exception.FileParseException;
import com.sokolmeteo.utils.exception.NoDevicePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    private final TcpInteraction tcpInteraction;
    private final RecordDao recordDao;
    private final HttpInteraction httpInteraction;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_COUNT = 100;

    public DataServiceImpl(TcpInteraction tcpInteraction, RecordDao recordDao, HttpInteraction httpInteraction) {
        this.tcpInteraction = tcpInteraction;
        this.recordDao = recordDao;
        this.httpInteraction = httpInteraction;
    }

    @Override
    public Long sendData(MultipartFile file, AuthSession session) {
        WeatherData data = parse(file);
        checkPermission(session.getId(), data.getDeviceImei());
        data.setAuthor(session.getLogin());
        return tcpInteraction.send(data);
    }

    @Override
    public Record getState(Long dataId, String author) {
        return recordDao.findByIdAndAuthor(dataId, author);
    }

    @Override
    public List<Record> getStates(Integer page, Integer count, String author) {
        Page<Record> pageableLogs = recordDao.findAllByAuthor(author, PageRequest.of(
                page != null ? page : DEFAULT_PAGE,
                count != null ? count : DEFAULT_COUNT,
                Sort.by("created").descending()));
        return pageableLogs.getContent();
    }

    private WeatherData parse(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            WeatherData data = new WeatherData();
            List<String> lines =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines().collect(Collectors.toList());
            if (lines.size() - 2 > 1000)
                throw new FileParseException("Нельзя отправлять свыше 1000 сообщений");
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
            throw new FileParseException("Не удалось прочитать файл");
        }
    }

    private void checkPermission(String sessionId, String imei) {
        List<Device> permittedDevices = httpInteraction.getPermittedDevice(sessionId);
        for (Device device : permittedDevices) {
            if (device.getImei().equals(imei)) return;
        }
        throw new NoDevicePermission("Нет доступа к устройству");
    }
}
