package com.sokolmeteo.sokol.http.dto;

import com.sokolmeteo.dao.model.Device;
import lombok.Getter;

import java.util.List;

@Getter
public class DevicesResponse {
    private List<Device> data;
}
