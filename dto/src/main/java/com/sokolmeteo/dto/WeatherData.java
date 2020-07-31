package com.sokolmeteo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class WeatherData {
    private Long id;
    private String loginMessage;
    private List<String> blackMessages = new ArrayList<>();
}
