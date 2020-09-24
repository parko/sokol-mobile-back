package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Device;
import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.dto.DevicesResponse;
import com.sokolmeteo.sokol.http.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpInteractionImpl implements HttpInteraction {
    private static final Logger logger = LoggerFactory.getLogger(HttpInteractionImpl.class);

    private final String URI;
    private final static String LOGIN_PATH = "/platform/api/user/login";
    private final static String DEVICES_PATH = "/api/device";


    public HttpInteractionImpl(String host) {
        URI = host;
    }

    @Override
    public String login(Login login) {
        try {
            ResponseEntity<LoginResponse> response = new RestTemplate().exchange(URI + LOGIN_PATH,
                    HttpMethod.POST, new HttpEntity<>(login), LoginResponse.class);
            List<String> cookies = response.getHeaders().get("Set-Cookie");
            if (cookies == null || cookies.size() < 1) {
                logger.warn("Response without session id received");
                throw new SokolHttpException("Внутренняя ошибка");
            }
            return cookies.get(0).split(";")[0];
        } catch (RestClientException e) {
            logger.warn("Exception on authorizing on SOKOLMETEO: " + e);
            throw new SokolHttpException("Неверный логин или пароль");
        }
    }

    @Override
    public List<Device> getPermittedDevice(String sessionId) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Cookie", sessionId);
            ResponseEntity<DevicesResponse> response = new RestTemplate().exchange(URI + DEVICES_PATH + "?count=500",
                    HttpMethod.POST, new HttpEntity<>(headers), DevicesResponse.class);
            if (response.getBody() == null || response.getBody().getData().size() < 1) {
                throw new SokolHttpException("Нет доступных устройств");
            }
            return response.getBody().getData();
        } catch (RestClientException e) {
            logger.warn("Exception on receiving permitted devices: " + e);
            throw new SokolHttpException("Внутренняя ошибка");
        }
    }
}
