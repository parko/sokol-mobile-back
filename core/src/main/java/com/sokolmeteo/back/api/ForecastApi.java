package com.sokolmeteo.back.api;

import com.sokolmeteo.back.service.ForecastApiService;
import com.sokolmeteo.dto.ForecastDto;
import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.utils.Path.ForecastPath;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForecastApi {
    private final ForecastApiService service;

    public ForecastApi(ForecastApiService service) {
        this.service = service;
    }

    @GetMapping(path = ForecastPath.ALL)
    public ValuableResponse<List<ForecastDto>> getAll(@CookieValue(name = "JSESSIONID") String sessionId,
                                         @RequestParam String deviceId,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate,
                                         @RequestParam(defaultValue = "0") int start,
                                         @RequestParam(defaultValue = "10") int count,
                                         @RequestParam(required = false) String sortField,
                                         @RequestParam(defaultValue = "desc") String sortDir) {
        return new ValuableResponse<>(service.getAll(sessionId, deviceId, startDate, endDate, start, count,
                sortField, sortDir));
    }
}
