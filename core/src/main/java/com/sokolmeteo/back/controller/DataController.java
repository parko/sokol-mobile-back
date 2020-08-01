package com.sokolmeteo.back.controller;

import com.sokolmeteo.back.service.DataService;
import com.sokolmeteo.back.service.LoginService;
import com.sokolmeteo.dao.model.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/data")
public class DataController {
    private final LoginService loginService;
    private final DataService dataService;

    public DataController(LoginService loginService, DataService dataService) {
        this.loginService = loginService;
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Long> uploadFile(@RequestParam String credentials, @RequestBody MultipartFile file) {
        String login = loginService.login(credentials);
        if (login == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return dataService.sendData(file, login);
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<Log> getState(@RequestParam String credentials, @PathVariable Long dataId) {
        String login = loginService.login(credentials);
        if (login == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return dataService.getState(dataId, login);
    }
}
