package com.sokolmeteo.back.service;

import java.util.List;
import java.util.Map;

public interface WRecordApiService {
    List<Map<String, Object>> getAll(String sessionId, String deviceId, String startDate, String endDate, String params);
}
