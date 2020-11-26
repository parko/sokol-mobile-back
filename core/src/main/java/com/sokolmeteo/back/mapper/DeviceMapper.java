package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.DeviceDto;
import com.sokolmeteo.sokol.http.model.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class, PermissionMapper.class})
public interface DeviceMapper {
    Device dtoToDevice(DeviceDto deviceDto);

    DeviceDto deviceToDto(Device device);
}
