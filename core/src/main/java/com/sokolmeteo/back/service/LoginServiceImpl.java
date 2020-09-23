package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.AuthSession;
import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolHttpException;
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
    public AuthSession login(String credentials) {
        Login login = decode(credentials);
        AuthSession session = new AuthSession();
        String sessionId = httpInteraction.login(login);
        logger.info("Authorized by " + login.getLogin());
        session.setLogin(login.getLogin());
        session.setId(sessionId);
        return session;
    }

    private Login decode(String credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(credentials));
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            return new Login(tokenizer.nextToken(), tokenizer.nextToken());
        } catch (Exception e) {
            throw new SokolHttpException("Неверная строка credentials");
        }
    }
}
