package com.sokolmeteo.sokol.http.service.impl;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolPath.AnalyticsPath;
import com.sokolmeteo.sokol.http.dto.WRecordResponse;
import com.sokolmeteo.sokol.http.service.WRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WRecordServiceImpl implements WRecordService {
    private final HttpInteraction httpInteraction;

    public WRecordServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    public List<Map<String, Object>> getWRecords(String sessionId, String deviceId, String startDate, String endDate,
                                                 String parameters) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        Map<String, Object> params = new HashMap<>();
        params.put("deviceId", deviceId);
        if (startDate != null)
            params.put("startDate", startDate);
        if (endDate != null)
            params.put("endDate", endDate);
        if (parameters != null)
            params.put("parameters", parameters);
        ResponseEntity<WRecordResponse> response = httpInteraction.postForList(AnalyticsPath.RECORD, headers, params,
                WRecordResponse.class);
        return response.getBody();
    }
}
