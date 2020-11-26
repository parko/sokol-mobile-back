package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.service.WRecordApiService;
import com.sokolmeteo.sokol.http.service.WRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WRecordApiServiceImpl implements WRecordApiService {
    private final WRecordService wRecordService;

    public WRecordApiServiceImpl(WRecordService wRecordService) {
        this.wRecordService = wRecordService;
    }

    @Override
    public List<Map<String, Object>> getAll(String sessionId, String deviceId, String startDate, String endDate, String params) {
        return wRecordService.getWRecords(sessionId, deviceId, startDate, endDate, params);
    }
}
