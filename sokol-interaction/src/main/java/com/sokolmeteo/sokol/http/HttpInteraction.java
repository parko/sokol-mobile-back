package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Login;

public interface HttpInteraction {
    boolean login(Login login);
}
