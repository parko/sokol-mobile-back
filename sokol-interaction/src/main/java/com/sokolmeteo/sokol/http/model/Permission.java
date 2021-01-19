package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Permission {
    @JsonProperty(value = "userId", required = true)
    private String userId;
    @JsonProperty(value = "userEmail")
    private String userEmail;
    @JsonProperty(value = "objectPermissionId", required = true)
    private String userPermissionId;
}
