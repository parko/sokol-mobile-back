package com.sokolmeteo.sokol.http.service;

import java.util.List;
import java.util.Map;

public interface WRecordService {
    List<Map<String, Object>> getWRecords(String sessionId, String deviceId, String startDate, String endDate, String parameters);
}
