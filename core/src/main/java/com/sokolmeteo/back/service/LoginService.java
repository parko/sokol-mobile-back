package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.AuthSession;

public interface LoginService {
    AuthSession login(String credentials);
}
