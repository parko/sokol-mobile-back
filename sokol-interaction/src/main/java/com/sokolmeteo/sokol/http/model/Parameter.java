package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Parameter {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "code", required = true)
    private String code;
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "expression")
    private String expression;
    @JsonProperty(value = "calculationOrder", defaultValue = "0")
    private int calculationOrder;
    @JsonProperty(value = "primary", defaultValue = "true")
    private boolean primary;
    @JsonProperty(value = "show", defaultValue = "true")
    private boolean show;
    @JsonProperty(value = "unit")
    private String unit;
    @JsonProperty(value = "minValue")
    private String minValue;
    @JsonProperty(value = "maxValue")
    private String maxValue;
    @JsonProperty(value = "color")
    private String color;
}
