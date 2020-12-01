package com.sokolmeteo.sokol.http.service.impl;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.HttpInteractionException;
import com.sokolmeteo.sokol.http.dto.LoginResponse;
import com.sokolmeteo.sokol.http.model.Login;
import com.sokolmeteo.sokol.http.model.Profile;
import com.sokolmeteo.sokol.http.SokolPath.UserPath;
import com.sokolmeteo.sokol.http.model.RecoveryLogin;
import com.sokolmeteo.sokol.http.service.AuthService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@EnableCaching
public class AuthServiceImpl implements AuthService {
    private final HttpInteraction httpInteraction;

    public AuthServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    @Cacheable(value = "login", key = "#login.login.concat('-').concat(#login.password)")
    public String login(Login login) {
        try {
            ResponseEntity<LoginResponse> response = httpInteraction.post(UserPath.LOGIN, login, LoginResponse.class);
            List<String> headers = response.getHeaders().get("Set-Cookie");
            if (headers == null)
                throw new HttpInteractionException("Internal error exception", "Внутренняя ошибка сервера");
            return headers.get(0).split(";")[0];
        } catch (HttpClientErrorException e) {
            throw new HttpInteractionException(e.getStatusText(), "Не удалось авторизоваться");
        }
    }

    @Override
    public String logout(String sessionId) {
        httpInteraction.post(UserPath.LOGOUT, "JSESSIONID=" + sessionId);
        return "OK";
    }

    @Override
    public String register(Profile profile) {
        ResponseEntity<LoginResponse> response =
                httpInteraction.post(UserPath.REGISTER, profile, LoginResponse.class);
        return response.getBody().getId();
    }

    @Override
    public String recover(String login) {
        RecoveryLogin recoveryLogin = new RecoveryLogin();
        recoveryLogin.setLogin(login);
        ResponseEntity<LoginResponse> response =
                httpInteraction.post(UserPath.RECOVER, recoveryLogin, LoginResponse.class);
        return response.getBody().isSuccess() ? "OK" : "FAULT";
    }
}
