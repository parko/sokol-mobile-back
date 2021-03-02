package com.sokolmeteo.back.api;

import com.sokolmeteo.back.service.WRecordApiService;
import com.sokolmeteo.dto.ParameterDto;
import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.utils.Path.WeatherRecordPath;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WRecordApi {
    private final WRecordApiService service;

    public WRecordApi(WRecordApiService service) {
        this.service = service;
    }

    @GetMapping(path = WeatherRecordPath.ALL)
    public ValuableResponse<List<ParameterDto>> getAll(@CookieValue(name = "JSESSIONID") String sessionId,
                                                       @RequestParam String deviceId,
                                                       @RequestParam(required = false) String startDate,
                                                       @RequestParam(required = false) String endDate,
                                                       @RequestParam(required = false) String parameters) {
        return new ValuableResponse<>(service.getAll(sessionId, deviceId, startDate, endDate, parameters));
    }
}
