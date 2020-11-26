package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Login {
    @JsonProperty(value = "loginType", required = true)
    private String loginType = "email";
    @JsonProperty(value = "login", required = true)
    private String login;
    @JsonProperty(value = "password", required = true)
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
