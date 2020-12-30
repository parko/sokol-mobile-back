package com.sokolmeteo.sokol.http.service.impl;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolPath.DevicePath;
import com.sokolmeteo.sokol.http.dto.DeviceResponse;
import com.sokolmeteo.sokol.http.dto.ParameterResponse;
import com.sokolmeteo.sokol.http.model.Device;
import com.sokolmeteo.sokol.http.model.Parameter;
import com.sokolmeteo.sokol.http.service.DeviceService;
import com.sokolmeteo.utils.exception.NoDevicePermissionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final HttpInteraction httpInteraction;

    public DeviceServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    public List<Device> getDevices(String sessionId, int start, int count, String sortField, String sortDir) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("count", count);
        params.put("sortDir", sortDir);
        if (sortField != null) params.put("sortField", sortField);
        ResponseEntity<DeviceResponse> response =
                httpInteraction.post(DevicePath.DEVICES, headers, params, DeviceResponse.class);
        return response.getBody().getData();
    }

    @Override
    public String save(String sessionId, Device device) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        ResponseEntity<DeviceResponse> response =
                httpInteraction.post(DevicePath.SAVE, headers, device, DeviceResponse.class);
        return response.getBody().isSuccess() ? "OK" : "FAULT";
    }

    @Override
    public String delete(String sessionId, String deviceId) {
        String path = DevicePath.DELETE.replaceFirst("\\{id\\}", deviceId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        ResponseEntity<DeviceResponse> response = httpInteraction.post(path, headers, DeviceResponse.class);
        return response.getBody().isSuccess() ? "OK" : "FAULT";
    }

    @Override
    public List<Parameter> getParams(String sessionId, String deviceId) {
        String path = DevicePath.PARAMETERS.replaceFirst("\\{id\\}", deviceId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        ResponseEntity<ParameterResponse> response = httpInteraction.postForList(path, headers, ParameterResponse.class);
        return response.getBody();
    }

    @Override
    public void checkPermission(String sessionId, String deviceImei) {
        for (Device device : getDevices(sessionId, 0, 500, null, "desc")) {
            if (device.getImei().equals(deviceImei))
                return;
        }
        throw new NoDevicePermissionException("No device permission", "Нет доступа к устройству");
    }
}
