package com.sokolmeteo.sokol.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sokolmeteo.sokol.http.model.Forecast;
import lombok.Getter;

import java.util.List;

@Getter
public class ForecastResponse implements SokolResponse {
    @JsonProperty(value = "pages")
    private int pages;
    @JsonProperty(value = "page")
    private int page;
    @JsonProperty(value = "totalCount")
    private int totalCount;
    @JsonProperty(value = "startRecord")
    private int start;
    @JsonProperty("data")
    private List<Forecast> data;
    @JsonProperty("success")
    private boolean success;
}
