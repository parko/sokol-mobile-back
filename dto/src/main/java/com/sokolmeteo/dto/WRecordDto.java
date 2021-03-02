package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WRecordDto {
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "value")
    private String value;
}
