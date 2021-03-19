package com.sokolmeteo.sokol.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sokolmeteo.sokol.http.model.Device;
import lombok.Getter;

import java.util.List;

public class DevicesResponse extends SokolResponse {
    @Getter
    @JsonProperty(value = "pages")
    private int pages;
    @Getter
    @JsonProperty(value = "page")
    private int page;
    @Getter
    @JsonProperty(value = "totalCount")
    private int totalCount;
    @Getter
    @JsonProperty(value = "startRecord")
    private int start;
    @Getter
    @JsonProperty("data")
    private List<Device> data;
}
