package com.sokolmeteo.sokol.http.service.impl;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolPath.ForecastPath;
import com.sokolmeteo.sokol.http.dto.ForecastResponse;
import com.sokolmeteo.sokol.http.model.Forecast;
import com.sokolmeteo.sokol.http.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final HttpInteraction httpInteraction;

    public ForecastServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    public List<Forecast> getForecasts(String sessionId, String deviceId, String startDate, String endDate, int start, int count, String sortField, String sortDir) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        Map<String, Object> params = new HashMap<>();
        params.put("deviceId", deviceId);
        params.put("start", start);
        params.put("count", count);
        params.put("sortDir", sortDir);
        if (sortField != null)
            params.put("sortField", sortField);
        if (startDate != null)
            params.put("startDate", startDate);
        if (endDate != null)
            params.put("endDate", endDate);
        ResponseEntity<ForecastResponse> response = httpInteraction.post(
                ForecastPath.FORECASTS, headers, params, ForecastResponse.class);
        return response.getBody().getData();
    }
}
