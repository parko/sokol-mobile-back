package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.mapper.ParameterMapper;
import com.sokolmeteo.back.service.WRecordApiService;
import com.sokolmeteo.dto.ParameterDto;
import com.sokolmeteo.dto.WRecordDto;
import com.sokolmeteo.sokol.http.model.Parameter;
import com.sokolmeteo.sokol.http.service.DeviceService;
import com.sokolmeteo.sokol.http.service.WRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WRecordApiServiceImpl implements WRecordApiService {
    private final WRecordService wRecordService;
    private final DeviceService deviceService;
    private final ParameterMapper parameterMapper;

    public WRecordApiServiceImpl(WRecordService wRecordService, DeviceService deviceService, ParameterMapper parameterMapper) {
        this.wRecordService = wRecordService;
        this.deviceService = deviceService;
        this.parameterMapper = parameterMapper;
    }

    @Override
    public List<ParameterDto> getAll(String sessionId, String deviceId, String startDate, String endDate, String params) {
        List<Map<String, Object>> records = wRecordService.getWRecords(sessionId, deviceId, startDate, endDate, params);
        List<ParameterDto> parameters = new ArrayList<>();
        for (Parameter parameter : deviceService.getParams(sessionId, deviceId)) {
            parameters.add(parameterMapper.parameterToDto(parameter));
        }
        return unit(parameters, records);
    }

    private List<ParameterDto> unit(List<ParameterDto> parameters, List<Map<String, Object>> data) {
        for (ParameterDto parameter : parameters) {
            List<WRecordDto> records = new ArrayList<>();
            for (Map<String, Object> record : data) {
                if (record.get(parameter.getCode()) != null) {
                    String date = record.get("date").toString();
                    String value = record.get(parameter.getCode()).toString();
                    records.add(new WRecordDto(date, value));
                }
            }
            parameter.setRecords(records);
        }
        return parameters;
    }
}
