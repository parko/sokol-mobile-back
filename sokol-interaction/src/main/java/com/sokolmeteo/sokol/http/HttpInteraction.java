package com.sokolmeteo.sokol.http;

import com.sokolmeteo.sokol.http.dto.SokolListResponse;
import com.sokolmeteo.sokol.http.dto.SokolResponse;
import com.sokolmeteo.sokol.http.model.Device;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface HttpInteraction {
    //регистрация
    <T extends SokolResponse> ResponseEntity<T> post(String path, Object body, Class<T> clazz);

    <T> ResponseEntity<T> get(String path, MultiValueMap<String, String> headers, Class<T> clazz);

    //деавторизация
    void post(String path, MultiValueMap<String, String> headers);

    //список устройств, список прогнозов, список анализов
    <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers,
                                                     Map<String, Object> params, Class<T> clazz);

    //сохранение устройства, сохранение анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers, Object body,
                                                     Class<T> clazz);

    //удаление устройства, удаление анализа
    <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers, Class<T> clazz);

    //список параметров
    <T extends SokolListResponse> ResponseEntity<T> postForList(String path, MultiValueMap<String, String> headers,
                                                                Class<T> clazz);

    //список показаний
    <T extends SokolListResponse> ResponseEntity<T> postForList(String path, MultiValueMap<String, String> headers,
                                                                Map<String, Object> params, Class<T> clazz);
}
