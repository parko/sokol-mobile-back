package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.mapper.AnalyseMapper;
import com.sokolmeteo.back.service.AnalyseApiService;
import com.sokolmeteo.dto.AnalyseDto;
import com.sokolmeteo.sokol.http.model.Analyse;
import com.sokolmeteo.sokol.http.service.AnalyseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyseApiServiceImpl implements AnalyseApiService {
    private final AnalyseService analyseService;
    private final AnalyseMapper analyseMapper;

    public AnalyseApiServiceImpl(AnalyseService analyseService, AnalyseMapper analyseMapper) {
        this.analyseService = analyseService;
        this.analyseMapper = analyseMapper;
    }

    @Override
    public List<AnalyseDto> getAll(String sessionId, String name, Boolean active, int start, int count, String sortField, String sortDir) {
        List<AnalyseDto> analysis = new ArrayList<>();
        for (Analyse analyse : analyseService.getAnalyse(sessionId, start, count, sortField, sortDir, name, active))
            analysis.add(analyseMapper.analyseToDto(analyse));
        return analysis;
    }

    @Override
    public String save(String sessionId, AnalyseDto analyseDto) {
        return analyseService.save(sessionId, analyseMapper.dtoToAnalyse(analyseDto));
    }

    @Override
    public String delete(String sessionId, String id) {
        return analyseService.delete(sessionId, id);
    }
}
