package com.sokolmeteo.back.api;

import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.back.service.DataApiService;
import com.sokolmeteo.model.entity.Record;
import com.sokolmeteo.utils.Path;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class DataApi {
    private final DataApiService service;

    public DataApi(DataApiService service) {
        this.service = service;
    }

    @GetMapping(Path.LOGIN)
    public ValuableResponse<String> checkCredentials(@RequestParam String credentials) {
        service.checkCredentials(credentials);
        return new ValuableResponse<>("OK");
    }

    @PostMapping(Path.DATA)
    public ValuableResponse<Long> uploadFile(@RequestParam String credentials, @RequestParam String station,
                                             @RequestParam Long start, @RequestParam Long end,
                                             @RequestBody MultipartFile file) {
        return new ValuableResponse<>(service.uploadFile(file, credentials, station, start, end));
    }

    @GetMapping(Path.DATA + "/{dataId}")
    public ValuableResponse<Record> getState(@RequestParam String credentials, @PathVariable Long dataId) {
        return new ValuableResponse<>(service.getState(credentials, dataId));
    }

    @GetMapping(Path.DATA)
    public ValuableResponse<List<Record>> getStates(@RequestParam String credentials,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "100") Integer count) {
        return new ValuableResponse<>(service.getStates(credentials, page, count));
    }
}