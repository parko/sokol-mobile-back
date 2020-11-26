package com.sokolmeteo.sokol.http.service;

import com.sokolmeteo.sokol.http.model.Analyse;

import java.util.List;

public interface AnalyseService {
    List<Analyse> getAnalyse(String sessionId, int start, int count, String sortField, String sortDir, String name,
                             Boolean active);

    String save(String sessionId, Analyse analyse);

    String delete(String sessionId, String analyseId);
}
