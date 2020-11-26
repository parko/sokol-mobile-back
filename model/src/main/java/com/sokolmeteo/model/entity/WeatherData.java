package com.sokolmeteo.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Getter @Setter
public class WeatherData {
    private Long id;
    private String author;
    private String station;
    private Long start;
    private Long end;
    private String loginMessage;
    private List<String> blackMessages = new ArrayList<>();

    public WeatherData(String station, Long start, Long end) {
        this.station = station;
        this.start = start;
        this.end = end;
    }

    public String getDeviceImei() {
        StringTokenizer tokenizer = new StringTokenizer(loginMessage, "#;");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    public String getDevicePassword() {
        StringTokenizer tokenizer = new StringTokenizer(loginMessage, ";\r");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }
}
