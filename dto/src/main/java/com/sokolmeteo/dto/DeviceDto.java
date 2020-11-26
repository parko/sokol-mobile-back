package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DeviceDto {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "imei", required = true)
    private String imei;
    @JsonProperty(value = "password", required = true)
    private String password;
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
    private List<ParameterDto> params;
    @JsonProperty(value = "permissions")
    private List<PermissionDto> permissions;
}
