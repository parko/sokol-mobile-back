package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissionDto {
    @JsonProperty(value = "userId", required = true)
    private String userId;
    @JsonProperty(value = "userEmail")
    private String userEmail;
    @JsonProperty(value = "userPermissionId", required = true)
    private String userPermissionId;
}
