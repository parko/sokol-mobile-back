package com.sokolmeteo.back.service;

import com.sokolmeteo.dto.AnalyseDto;

import java.util.List;

public interface AnalyseApiService {
    List<AnalyseDto> getAll(String sessionId, String name, Boolean active, int start, int count, String sortField,
                            String sortDir);

    String save(String sessionId, AnalyseDto analyseDto);

    String delete(String sessionId, String id);
}
