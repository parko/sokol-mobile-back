package com.sokolmeteo.sokol.http.dto;

import com.sokolmeteo.dao.model.Device;
import lombok.Getter;

import java.util.List;

@Getter
public class DevicesResponse {
    private Integer pages;
    private Integer page;
    private Integer totalCount;
    private Integer queriedCount;
    private Integer startRecord;
    private List<Device> data;
    private Integer recordsTotal;
    private Integer recordsFiltered;
}
