package com.sokolmeteo.back.controller;

import com.sokolmeteo.BaseResponse;
import com.sokolmeteo.DataResponse;
import com.sokolmeteo.back.service.DataService;
import com.sokolmeteo.back.service.LoginService;
import com.sokolmeteo.dao.model.Record;
import com.sokolmeteo.utils.Path;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
public class DataController {
    private final LoginService loginService;
    private final DataService dataService;

    public DataController(LoginService loginService, DataService dataService) {
        this.loginService = loginService;
        this.dataService = dataService;
    }

    @GetMapping(Path.LOGIN)
    public BaseResponse authorize(@RequestParam String credentials) {
        loginService.login(credentials);
        return new BaseResponse("OK");
    }

    @PostMapping(Path.DATA)
    public DataResponse<Long> uploadFile(@RequestParam String credentials, @RequestParam String station,
                                         @RequestParam Long start, @RequestParam Long end,
                                         @RequestBody MultipartFile file) {
        return new DataResponse<>(dataService.sendData(file, credentials, station, start, end));
    }

    @GetMapping(Path.DATA + "/{dataId}")
    public DataResponse<Record> getState(@RequestParam String credentials, @PathVariable Long dataId) {
        String login = loginService.login(credentials).getLogin();
        return new DataResponse<>(dataService.getState(dataId, login));
    }

    @GetMapping(Path.DATA)
    public DataResponse<List<Record>> getStates(@RequestParam String credentials,
                                                  @RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer count) {
        String login = loginService.login(credentials).getLogin();
        return new DataResponse<>(dataService.getStates(page, count, login));
    }
}