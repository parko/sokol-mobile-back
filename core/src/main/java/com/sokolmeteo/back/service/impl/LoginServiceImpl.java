package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.service.LoginService;
import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.HttpInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.StringTokenizer;

@Service
@EnableCaching
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

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
            logger.info("Authorized by " + login);
            return login;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Error on authorizing");
            return null;
        }

    }
}
