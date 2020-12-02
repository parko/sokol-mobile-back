package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class AnalyseDto {
    @JsonProperty("id")
    private String id;
    @NotBlank(message = "Введите наименование")
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "active")
    private boolean active;
    @NotBlank(message = "Введите формулу расчета")
    @JsonProperty(value = "expression")
    private String expression;
    @NotBlank(message = "Введите текст уведомления")
    @JsonProperty(value = "notificationContent")
    private String notificationContent;
    @NotBlank(message = "Введите уровень уведомлений")
    @JsonProperty(value = "notificationLevelId")
    private String notificationLevel;
    @JsonProperty(value = "delayBetweenExecution")
    private Integer delayBetweenExecution;
    @JsonProperty(value = "userId")
    private String userId;
    @NotBlank(message = "Введите пороговое значение")
    @JsonProperty(value = "expressionEdgeValue")
    private String expressionEdgeValue;
}
