package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LoginDto {
    @JsonProperty(value = "login")
    private String login;
    @JsonProperty(value = "password")
    private String password;
}
