package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Device;
import com.sokolmeteo.dao.model.Login;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpInteractionImpl implements HttpInteraction {
    private final String URI;
    private final static String LOGIN_PATH = "/platform/api/user/login";
    private final static String DEVICES_PATH = "/api/device";


    public HttpInteractionImpl(String host) {
        URI = host;
    }

    @Override
    public String login(Login login) {
        ResponseEntity<LoginResponse> responseEntity = new RestTemplate().exchange(URI + LOGIN_PATH,
                HttpMethod.POST, new HttpEntity<>(login), LoginResponse.class);
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        if (cookies != null && cookies.size() > 0)
            return cookies.get(0).split(";")[0];
        throw new RestClientException("Error on authorizing");
    }

    @Override
    public List<Device> getPermittedDevice(String sessionId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", sessionId);
        ResponseEntity<DevicesResponse> response = new RestTemplate().exchange(URI + DEVICES_PATH + "?count=500",
                HttpMethod.POST, new HttpEntity<>(headers), DevicesResponse.class);
        if (response.getBody() != null) return response.getBody().getData();
        throw new RestClientException("Error on getting devices");
    }
}
