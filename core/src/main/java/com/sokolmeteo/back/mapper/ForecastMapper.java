package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.ForecastDto;
import com.sokolmeteo.sokol.http.model.Forecast;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForecastMapper {
    Forecast dtoToForecast(ForecastDto forecastDto);

    ForecastDto forecastToDto(Forecast forecast);
}
