package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecoveryLogin {
    @JsonProperty(value = "loginType")
    private String loginType = "email";
    @JsonProperty(value = "login", required = true)
    private String login;
}
