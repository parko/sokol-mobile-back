package com.sokolmeteo.sokol.http.service;

import com.sokolmeteo.sokol.http.model.Forecast;

import java.util.List;

public interface ForecastService {
    List<Forecast> getForecasts(String sessionId, String deviceId, String startDate, String endDate, int start, int count,
                                String sortField, String sortDir);
}
