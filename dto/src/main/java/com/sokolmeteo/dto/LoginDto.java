package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Введите логин")
    @JsonProperty(value = "login")
    private String login;
    @NotBlank(message = "Введите пароль")
    @JsonProperty(value = "password")
    private String password;
}
