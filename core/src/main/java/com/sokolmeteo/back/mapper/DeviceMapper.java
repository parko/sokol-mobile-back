package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.DeviceDto;
import com.sokolmeteo.sokol.http.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class, PermissionMapper.class})
public interface DeviceMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Device dtoToNewDevice(DeviceDto deviceDto);

    @Mappings({
            @Mapping(target = "params", ignore = true),
            @Mapping(target = "permissions", ignore = true)
    })
    Device dtoToDevice(DeviceDto deviceDto);

    DeviceDto deviceToDto(Device device);
}
