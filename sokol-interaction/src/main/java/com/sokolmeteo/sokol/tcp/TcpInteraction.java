package com.sokolmeteo.sokol.tcp;

import com.sokolmeteo.dao.model.WeatherData;

public interface TcpInteraction {
    Long send(WeatherData data);
}
