package com.sokolmeteo.sokol.http;

import com.sokolmeteo.sokol.http.dto.SokolResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HttpInteraction {
    //регистрация
    <T extends SokolResponse> ResponseEntity<T> post(String path, Object body, Class<T> clazz);

    //разавторизация
    void post(String path, String cookies);

    //список устройств, список показаний, список прогнозов, список анализов
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies,
                                                     Map<String, Object> params, Class<T> clazz);

    //сохранение устройства, сохранение анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Object body,
                                                     Class<T> clazz);

    //удаление устройства, список параметров, удаление анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Class<T> clazz);
}
