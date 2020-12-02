package com.sokolmeteo.back.api;

import com.sokolmeteo.back.service.AnalyseApiService;
import com.sokolmeteo.dto.AnalyseDto;
import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.utils.Path.AnalysePath;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnalyseApi {
    private final AnalyseApiService service;

    public AnalyseApi(AnalyseApiService service) {
        this.service = service;
    }

    @GetMapping(path = AnalysePath.ALL)
    public ValuableResponse<List<AnalyseDto>> getAll(@CookieValue(name = "JSESSIONID") String sessionId,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) Boolean active,
                                                     @RequestParam(defaultValue = "0") int start,
                                                     @RequestParam(defaultValue = "10") int count,
                                                     @RequestParam(required = false) String sortField,
                                                     @RequestParam(defaultValue = "desc") String sortDir) {
        return new ValuableResponse<>(service.getAll(sessionId, name, active, start, count, sortField, sortDir));
    }

    @PostMapping(path = AnalysePath.SAVE)
    public ValuableResponse<String> save(@CookieValue(name = "JSESSIONID") String sessionId,
                                         @Valid @RequestBody AnalyseDto analyseDto) {
        return new ValuableResponse<>(service.save(sessionId, analyseDto));
    }

    @DeleteMapping(path = AnalysePath.DELETION + "/{id}")
    public ValuableResponse<String> delete(@CookieValue(name = "JSESSIONID") String sessionId, @PathVariable String id) {
        return new ValuableResponse<>(service.delete(sessionId, id));
    }
}
