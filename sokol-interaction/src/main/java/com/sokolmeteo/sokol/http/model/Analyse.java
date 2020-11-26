package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Analyse {
    @JsonProperty("id")
    private String id;
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "active")
    private Boolean active;
    @JsonProperty(value = "expression")
    private String expression;
    @JsonProperty(value = "notificationContent")
    private String notificationContent;
    @JsonProperty(value = "notificationLevelId")
    private String notificationLevel;
    @JsonProperty(value = "delayBetweenExecution")
    private Integer delayBetweenExecution;
    @JsonProperty(value = "userId")
    private String userId;
    @JsonProperty(value = "expressionEdgeValue")
    private String expressionEdgeValue;
}
