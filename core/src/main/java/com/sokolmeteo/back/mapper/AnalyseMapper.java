package com.sokolmeteo.back.mapper;

import com.sokolmeteo.dto.AnalyseDto;
import com.sokolmeteo.sokol.http.model.Analyse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalyseMapper {
    Analyse dtoToAnalyse(AnalyseDto analyseDto);

    AnalyseDto analyseToDto(Analyse analyse);
}
