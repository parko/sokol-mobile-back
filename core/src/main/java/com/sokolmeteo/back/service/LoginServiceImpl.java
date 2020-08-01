package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.HttpInteraction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.StringTokenizer;

@Service
@EnableCaching
public class LoginServiceImpl implements LoginService {
    private final HttpInteraction httpInteraction;

    public LoginServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    @Cacheable(value = "authorize", key = "#credentials")
    public String login(String credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(credentials));
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            String login = tokenizer.nextToken();
            httpInteraction.login(new Login(login, tokenizer.nextToken()));
            return login;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
