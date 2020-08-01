package com.sokolmeteo.dao.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Login {
    private String loginType;
    private String login;
    private String password;

    public Login(String login, String password) {
        this.loginType = "email";
        this.login = login;
        this.password = password;
    }
}
