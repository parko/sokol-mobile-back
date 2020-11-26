package com.sokolmeteo.back.api;

import com.sokolmeteo.back.service.DeviceApiService;
import com.sokolmeteo.dto.DeviceDto;
import com.sokolmeteo.dto.ParameterDto;
import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.utils.Path.DevicePath;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceApi {
    private final DeviceApiService service;

    public DeviceApi(DeviceApiService service) {
        this.service = service;
    }

    @GetMapping(path = DevicePath.ALL)
    public ValuableResponse<List<DeviceDto>> getAll(@CookieValue(name = "JSESSIONID") String sessionId,
                                                    @RequestParam(defaultValue = "0") int start,
                                                    @RequestParam(defaultValue = "10") int count,
                                                    @RequestParam(required = false) String sortField,
                                                    @RequestParam(defaultValue = "desc") String sortDir) {
        return new ValuableResponse<>(service.getAll(sessionId, start, count, sortField, sortDir));
    }

    @PostMapping(path = DevicePath.SAVE)
    public ValuableResponse<String> save(@CookieValue(name = "JSESSIONID") String sessionId,
                                         @RequestBody DeviceDto deviceDto) {
        return new ValuableResponse<>(service.save(sessionId, deviceDto));
    }

    @DeleteMapping(path = DevicePath.DELETION + "/{id}")
    public ValuableResponse<String> delete(@CookieValue(name = "JSESSIONID") String sessionId, @PathVariable String id) {
        return new ValuableResponse<>(service.delete(sessionId, id));
    }

    @GetMapping(path = DevicePath.PARAMS + "/{id}")
    public ValuableResponse<List<ParameterDto>> getParams(@CookieValue(name = "JSESSIONID") String sessionId,
                                                          @PathVariable String id) {
        return new ValuableResponse<>(service.getParams(sessionId, id));
    }
}
