package com.sokolmeteo.back.service;

import com.sokolmeteo.dto.ParameterDto;

import java.util.List;
import java.util.Map;

public interface WRecordApiService {
    List<ParameterDto> getAll(String sessionId, String deviceId, String startDate, String endDate, String params);
}
