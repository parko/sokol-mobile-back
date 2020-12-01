package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter @Setter
public class ProfileDto {
    @NotBlank(message = "Введите email")
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @NotBlank(message = "Введите пароль")
    @JsonProperty("password")
    private String password;
    @JsonProperty("fields")
    private Map<String, String> fields;
}
