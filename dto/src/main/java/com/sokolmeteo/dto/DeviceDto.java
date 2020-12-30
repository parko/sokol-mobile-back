package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DeviceDto {
    @JsonProperty(value = "id")
    private String id;
    @NotBlank(message = "Введите наименование")
    @JsonProperty(value = "name", required = true)
    private String name;
    @NotBlank(message = "Введите imei")
    @JsonProperty(value = "imei", required = true)
    private String imei;
    @NotBlank(message = "Введите пароль")
    @JsonProperty(value = "password", required = true)
    private String password;
    @JsonProperty(value = "latitude")
    private String latitude;
    @JsonProperty(value = "longitude")
    private String longitude;
    @JsonProperty(value = "lastDataReceivedDt")
    private String lastDataReceivedDt;
    @JsonProperty(value = "forecastActive", defaultValue = "false")
    private boolean forecastActive;
    @JsonProperty(value = "exactFarmingActive", defaultValue = "false")
    private boolean exactFarmingActive;
    @JsonProperty(value = "permittedToRead")
    private boolean permittedToRead;
    @JsonProperty(value = "permittedToWrite")
    private boolean permittedToWrite;
    @JsonProperty(value = "permittedToDelete")
    private boolean permittedToDelete;
    @JsonProperty(value = "params")
    private List<ParameterDto> params = new ArrayList<>();
    @JsonProperty(value = "permissions")
    private List<PermissionDto> permissions;
}
