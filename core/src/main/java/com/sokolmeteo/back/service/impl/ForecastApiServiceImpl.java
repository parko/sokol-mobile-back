package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.mapper.ForecastMapper;
import com.sokolmeteo.back.service.ForecastApiService;
import com.sokolmeteo.dto.ForecastDto;
import com.sokolmeteo.sokol.http.model.Forecast;
import com.sokolmeteo.sokol.http.service.ForecastService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastApiServiceImpl implements ForecastApiService {
    private final ForecastService forecastService;
    private final ForecastMapper forecastMapper;

    public ForecastApiServiceImpl(ForecastService forecastService, ForecastMapper forecastMapper) {
        this.forecastService = forecastService;
        this.forecastMapper = forecastMapper;
    }

    @Override
    public List<ForecastDto> getAll(String sessionId, String deviceId, String startDate, String endDate, int start,
                                    int count, String sortField, String sortDir) {
        List<ForecastDto> forecasts = new ArrayList<>();
        for (Forecast forecast : forecastService.getForecasts(sessionId, deviceId, startDate, endDate, start, count,
                sortField, sortDir))
            forecasts.add(forecastMapper.forecastToDto(forecast));
        return forecasts;
    }
}
