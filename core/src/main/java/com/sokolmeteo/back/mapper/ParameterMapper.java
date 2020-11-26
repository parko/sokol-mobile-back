package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.ParameterDto;
import com.sokolmeteo.sokol.http.model.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper {
    Parameter dtoToParameter(ParameterDto parameterDto);

    ParameterDto parameterToDto(Parameter parameter);
}
