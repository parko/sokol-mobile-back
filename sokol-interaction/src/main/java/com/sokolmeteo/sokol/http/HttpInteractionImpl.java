package com.sokolmeteo.sokol.http;

import com.sokolmeteo.sokol.config.ConnectionProps;
import com.sokolmeteo.sokol.http.dto.SokolListResponse;
import com.sokolmeteo.sokol.http.dto.SokolResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(body, HttpMethod.POST, uri);
        logger.debug("Sending request: {}", request.toString());
        return restTemplate.exchange(request, clazz);
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> get(String path, MultiValueMap<String, String> headers, Class<T> clazz) {
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        logger.debug("Sending request: {}", request.toString());
        try {
            logger.debug("Sending request: {}", request.toString());
            return restTemplate.exchange(request, clazz);
        } catch (RestClientException e) {
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        }
    }

    @Override
    public void post(String path, MultiValueMap<String, String> headers) {
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.POST, uri);
        logger.debug("Sending request: {}", request.toString());
        restTemplate.exchange(request, String.class);
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers,
                                                            Map<String, Object> params, Class<T> clazz) {
        return execute(path, headers, params, clazz);
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers,
                                                            Object body, Class<T> clazz) {
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(body, headers, HttpMethod.POST, uri);
        logger.debug("Sending request: {}", request.toString());
        return execute(request, clazz);
    }

    @Override
    public <T extends SokolResponse> ResponseEntity<T> post(String path, MultiValueMap<String, String> headers,
                                                            Class<T> clazz) {
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.POST, uri);
        logger.debug("Sending request: {}", request.toString());
        return execute(request, clazz);
    }

    @Override
    public <T extends SokolListResponse> ResponseEntity<T> postForList(String path, MultiValueMap<String, String> headers,
                                                                       Class<T> clazz) {
        URI uri = buildUri(path);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.POST, uri);
        try {
            logger.debug("Sending request: {}", request.toString());
            return restTemplate.exchange(request, clazz);
        } catch (RestClientException e) {
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        }
    }

    @Override
    public <T extends SokolListResponse> ResponseEntity<T> postForList(String path, MultiValueMap<String, String> headers,
                                                                       Map<String, Object> params, Class<T> clazz) {
        return execute(path, headers, params, clazz);
    }

    private <T> ResponseEntity<T> execute(String path, MultiValueMap<String, String> headers, Map<String, Object> params,
                                          Class<T> clazz) {
        String parameters = prepareParams(params);
        URI uri = buildUri(path + parameters);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.POST, uri);
        logger.debug("Sending request: {}", request.toString());
        return execute(request, clazz);
    }

    private <T> ResponseEntity<T> execute(RequestEntity<Object> request, Class<T> clazz) {
        ResponseEntity<T> response = restTemplate.exchange(request, clazz);
        if (response.getStatusCode().equals(HttpStatus.FOUND))
            throw new HttpInteractionException("Unauthorized", "Сессия не авторизована");
        else return response;
    }

    private URI buildUri(String path) {
        String fullPath = connectionProps.getHttpHost() + path;
        return URI.create(fullPath);
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
