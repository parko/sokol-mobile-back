package com.sokolmeteo.sokol.http.service;

import com.sokolmeteo.sokol.http.model.Login;
import com.sokolmeteo.sokol.http.model.Profile;

public interface AuthService {
    String login(Login login);

    String logout(String sessionId);

    String register(Profile profile);

    String recover(String login);
}
