package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.mapper.DeviceMapper;
import com.sokolmeteo.back.mapper.ParameterMapper;
import com.sokolmeteo.back.service.DeviceApiService;
import com.sokolmeteo.dto.DeviceDto;
import com.sokolmeteo.dto.ParameterDto;
import com.sokolmeteo.sokol.http.model.Device;
import com.sokolmeteo.sokol.http.model.Parameter;
import com.sokolmeteo.sokol.http.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceApiServiceImpl implements DeviceApiService {
    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;
    private final ParameterMapper parameterMapper;

    public DeviceApiServiceImpl(DeviceService deviceService, DeviceMapper deviceMapper, ParameterMapper parameterMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
        this.parameterMapper = parameterMapper;
    }

    @Override
    public List<DeviceDto> getAll(String sessionId, int start, int count, String sortField, String sortDir) {
        List<DeviceDto> devices = new ArrayList<>();
        for (Device device : deviceService.getDevices(sessionId, start, count, sortField, sortDir))
            devices.add(deviceMapper.deviceToDto(device));
        return devices;
    }

    @Override
    public String save(String sessionId, DeviceDto deviceDto) {
        Device device = deviceDto.getId() != null ? deviceMapper.renewDevice(deviceDto) : deviceMapper.dtoToNewDevice(deviceDto);
        return deviceService.save(sessionId, device);
    }

    @Override
    public String delete(String sessionId, String deviceId) {
        return deviceService.delete(sessionId, deviceId);
    }

    @Override
    public List<ParameterDto> getParams(String sessionId, String deviceId) {
        List<ParameterDto> params = new ArrayList<>();
        for (Parameter parameter : deviceService.getParams(sessionId, deviceId))
            params.add(parameterMapper.parameterToDto(parameter));
        return params;
    }
}
