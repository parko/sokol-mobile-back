package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Login;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class HttpInteractionImpl implements HttpInteraction {
    private final String URI;


    public HttpInteractionImpl(String host) {
        URI = String.format("%s/platform/api/user/login", host);
    }

    public void login(Login login) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(URI, HttpMethod.POST, new HttpEntity<>(login), HttpResponse.class);
    }
}
