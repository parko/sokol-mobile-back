package com.sokolmeteo.dao.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthSession {
    public AuthSession(String login, String id) {
        this.login = login;
        this.id = id;
    }

    private String login;
    private String id;
}
