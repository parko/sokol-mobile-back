package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Login;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpInteractionImpl implements HttpInteraction {
    private static final String URI = "https://sokolmeteo.com/platform/api/user/login";

    @Override
    public boolean login(Login login) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(URI, HttpMethod.POST, new HttpEntity<>(login), HttpResponse.class);
        return true;
    }
}
