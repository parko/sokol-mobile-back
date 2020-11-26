package com.sokolmeteo.back.service;

import com.sokolmeteo.dto.DeviceDto;
import com.sokolmeteo.dto.ParameterDto;

import java.util.List;

public interface DeviceApiService {
    List<DeviceDto> getAll(String sessionId, int start, int count, String sortField, String sortDir);

    String save(String sessionId, DeviceDto deviceDto);

    String delete(String sessionId, String deviceId);

    List<ParameterDto> getParams(String sessionId, String deviceId);
}
