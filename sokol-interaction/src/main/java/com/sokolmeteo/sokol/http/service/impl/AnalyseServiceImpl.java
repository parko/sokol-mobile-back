package com.sokolmeteo.sokol.http.service.impl;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolPath.AnalysePath;
import com.sokolmeteo.sokol.http.dto.AnalyseResponse;
import com.sokolmeteo.sokol.http.model.Analyse;
import com.sokolmeteo.sokol.http.service.AnalyseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyseServiceImpl implements AnalyseService {
    private final HttpInteraction httpInteraction;

    public AnalyseServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    public List<Analyse> getAnalyse(String sessionId, int start, int count, String sortField, String sortDir,
                                    String name, Boolean active) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("count", count);
        params.put("sortDir", sortDir);
        if (sortField != null) params.put("sortField", sortField);
        if (name != null) params.put("name", name);
        if (active != null) params.put("active", active);
        ResponseEntity<AnalyseResponse> response =
                httpInteraction.post(AnalysePath.ANALYSIS, headers, params, AnalyseResponse.class);
        return response.getBody().getData();
    }

    @Override
    public String save(String sessionId, Analyse analyse) {
        String path = analyse.getId() != null ?
                AnalysePath.SAVE.replaceFirst("\\{id\\}", analyse.getId()) : AnalysePath.SAVE;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        ResponseEntity<AnalyseResponse> response =
                httpInteraction.post(path, headers, analyse, AnalyseResponse.class);
        return response.getBody().isSuccess() ? "OK" : "FAULT";
    }

    @Override
    public String delete(String sessionId, String analyseId) {
        String path = AnalysePath.DELETE.replaceFirst("\\{id\\}", analyseId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        ResponseEntity<AnalyseResponse> response =
                httpInteraction.post(path, headers, AnalyseResponse.class);
        return response.getBody().isSuccess() ? "OK" : "FAULT";
    }
}
