package com.sokolmeteo.sokol.http;

import com.sokolmeteo.sokol.http.dto.SokolListResponse;
import com.sokolmeteo.sokol.http.dto.SokolResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HttpInteraction {
    //регистрация
    <T extends SokolResponse> ResponseEntity<T> post(String path, Object body, Class<T> clazz);

    //деавторизация
    void post(String path, String cookies);

    //список устройств, список прогнозов, список анализов
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies,
                                                     Map<String, Object> params, Class<T> clazz);

    //сохранение устройства, сохранение анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Object body,
                                                     Class<T> clazz);

    //удаление устройства, удаление анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Class<T> clazz);

    //список параметров
    <T extends SokolListResponse> ResponseEntity<T> postForList(String path, String cookies, Class<T> clazz);

    //список показаний
    <T extends SokolListResponse> ResponseEntity<T> postForList(String path, String cookies, Map<String, Object> params,
                                                                Class<T> clazz);
}
