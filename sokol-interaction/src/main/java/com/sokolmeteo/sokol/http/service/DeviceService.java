package com.sokolmeteo.sokol.http.service;

import com.sokolmeteo.sokol.http.model.Device;
import com.sokolmeteo.sokol.http.model.Parameter;

import java.util.List;

public interface DeviceService {
    List<Device> getDevices(String sessionId, int start, int count, String sortField, String sortDir);

    Device getDevice(String sessionId, String id);

    String save(String sessionId, Device device);

    String delete(String sessionId, String deviceId);

    List<Parameter> getParams(String sessionId, String deviceId);

    void checkPermission(String sessionId, String deviceImei);
}
