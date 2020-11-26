package com.sokolmeteo.sokol.tcp;

import com.sokolmeteo.model.entity.WeatherData;

public interface TcpInteraction {
    Long send(WeatherData data);
}
