package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.dao.model.Log;
import com.sokolmeteo.dao.model.WeatherData;
import com.sokolmeteo.dao.repo.LogDao;
import com.sokolmeteo.sokol.tcp.TcpClient;
import com.sokolmeteo.sokol.tcp.TcpClientException;

import java.io.IOException;

public class DataSenderService extends Thread {

    private WeatherData data;
    private Log log;
    private final LogDao logDao;
    private final TcpClient tcpClient;

    public DataSenderService(LogDao logDao, TcpClient tcpClient) {
        this.logDao = logDao;
        this.tcpClient = tcpClient;
    }

    public DataSenderService setData(WeatherData data) {
        this.data = data;
        return this;
    }

    public DataSenderService setLog(Log log) {
        this.log = log;
        return this;
    }

    @Override
    public void run() {
        for (String message : data.getBlackMessages()) {
            try {
                tcpClient.sendMessage(data.getLoginMessage(), message);
            } catch (IOException e) {
                e.printStackTrace();
                log.fault(e.getMessage(), null);
                logDao.save(log);
                return;
            } catch (TcpClientException e) {
                e.printStackTrace();
                log.fault(e.getMessage(), e.getDetails());
                logDao.save(log);
                return;
            }
        }
        log.success();
        logDao.save(log);
    }
}
