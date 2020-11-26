package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Forecast {
    @JsonProperty(value = "id", required = true)
    private String id;
    @JsonProperty(value = "created", required = true)
    private String created;
    @JsonProperty(value = "changed", required = true)
    private String changed;
    @JsonProperty(value = "forecastDt", required = true)
    private String forecastDt;
    @JsonProperty(value = "tMin")
    private Double tMin;
    @JsonProperty(value = "tMax")
    private Double tMax;
    @JsonProperty(value = "rNight")
    private Double rNight;
    @JsonProperty(value = "rDay")
    private Double rDay;
    @JsonProperty(value = "wsNight")
    private Double wsNight;
    @JsonProperty(value = "wsDay")
    private Double wsDay;
    @JsonProperty(value = "wdNight")
    private Double wdNight;
    @JsonProperty(value = "wdDay")
    private Double wdDay;
    @JsonProperty(value = "hmNight")
    private Double hmNight;
    @JsonProperty(value = "hmDay")
    private Double hmDay;
    @JsonProperty(value = "p0Night")
    private Double p0Night;
    @JsonProperty(value = "p0Day")
    private Double p0Day;
}
