package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.PermissionDto;
import com.sokolmeteo.sokol.http.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission dtoToPermission(PermissionDto permissionDto);

    PermissionDto permissionToDto(Permission permission);
}
