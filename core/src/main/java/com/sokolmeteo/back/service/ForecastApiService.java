package com.sokolmeteo.back.service;

import com.sokolmeteo.dto.ForecastDto;

import java.util.List;

public interface ForecastApiService {
    List<ForecastDto> getAll(String sessionId, String deviceId, String startDate, String endDate, int start, int count,
                             String sortField, String sortDir);
}
