package com.sokolmeteo.sokol.http;

import com.sokolmeteo.sokol.config.ConnectionProps;
import com.sokolmeteo.sokol.http.dto.SokolListResponse;
import com.sokolmeteo.sokol.http.dto.SokolResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpInteractionImpl implements HttpInteraction {
    private static final Logger logger = LoggerFactory.getLogger(HttpInteractionImpl.class);

    private final RestTemplate restTemplate;
    private final ConnectionProps connectionProps;

    public HttpInteractionImpl(RestTemplate restTemplate, ConnectionProps connectionProps) {
        this.restTemplate = restTemplate;
        this.connectionProps = connectionProps;
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, Object body, Class<T> clazz) {
        return restTemplate.exchange(prepareUrl(path), HttpMethod.POST, new HttpEntity<>(body), clazz);
    }

    @Override
    public void post(String path, String cookies) {
        restTemplate.exchange(prepareUrl(path), HttpMethod.POST, new HttpEntity<>(prepareHeaders(cookies)), String.class);
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Map<String, Object> params,
                                                            Class<T> clazz) {
        ResponseEntity<T> response =
                restTemplate.exchange(prepareUrl(path) + prepareParams(params), HttpMethod.POST,
                        new HttpEntity<>(prepareHeaders(cookies)), clazz);
        if (response.getStatusCode().equals(HttpStatus.FOUND))
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        else return response;
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Object body, Class<T> clazz) {
        ResponseEntity<T> response = restTemplate.exchange(prepareUrl(path), HttpMethod.POST,
                new HttpEntity<>(body, prepareHeaders(cookies)), clazz);
        if (response.getStatusCode().equals(HttpStatus.FOUND))
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        else return response;
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, String cookies, Class<T> clazz) {
        ResponseEntity<T> response = restTemplate.exchange(prepareUrl(path), HttpMethod.POST,
                new HttpEntity<>(prepareHeaders(cookies)), clazz);
        if (response.getStatusCode().equals(HttpStatus.FOUND))
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        else return response;
    }

    @Override
    public <T extends SokolListResponse> ResponseEntity<T> postForList(String path, String cookies, Class<T> clazz) {
        try {
            return restTemplate.exchange(prepareUrl(path), HttpMethod.POST,
                    new HttpEntity<>(prepareHeaders(cookies)), clazz);
        } catch (RestClientException e) {
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        }
    }

    @Override
    public <T extends SokolListResponse> ResponseEntity<T> postForList(String path, String cookies,
                                                                       Map<String, Object> params, Class<T> clazz) {
        ResponseEntity<T> response =
                restTemplate.exchange(prepareUrl(path) + prepareParams(params), HttpMethod.POST,
                        new HttpEntity<>(prepareHeaders(cookies)), clazz);
        if (response.getStatusCode().equals(HttpStatus.FOUND))
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        else return response;
    }

    private String prepareUrl(String path) {
        return connectionProps.getHttpHost() + path;
    }

    private MultiValueMap<String, String> prepareHeaders(String cookies) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", cookies);
        return headers;
    }

    private String prepareParams(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if (params.size() > 0) {
            sb.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        return sb.toString();
    }
}
