package com.sokolmeteo.sokol.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Permission {
    @JsonProperty(value = "userEmail", required = true)
    private String userEmail;
    @JsonProperty(value = "userPermissionId", required = true)
    private String userPermissionId;
}
