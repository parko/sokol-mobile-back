package com.sokolmeteo.back.controller;

import com.sokolmeteo.back.service.DataService;
import com.sokolmeteo.back.service.LoginService;
import com.sokolmeteo.dao.model.AuthSession;
import com.sokolmeteo.dao.model.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
public class DataController {
    private final LoginService loginService;
    private final DataService dataService;

    public DataController(LoginService loginService, DataService dataService) {
        this.loginService = loginService;
        this.dataService = dataService;
    }

    @GetMapping("login")
    public ResponseEntity<String> authorize(@RequestParam String credentials) {
        String login = loginService.login(credentials).getLogin();
        return login != null ? new ResponseEntity<>(login, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("data")
    public ResponseEntity<Long> uploadFile(@RequestParam String credentials, @RequestBody MultipartFile file) {
        AuthSession session = loginService.login(credentials);
        if (session.getLogin() == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return dataService.sendData(file, session);
    }

    @GetMapping("data/{dataId}")
    public ResponseEntity<Log> getState(@RequestParam String credentials, @PathVariable Long dataId) {
        String login = loginService.login(credentials).getLogin();
        if (login == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return dataService.getState(dataId, login);
    }

    @GetMapping("data")
    public ResponseEntity<List<Log>> getStates(@RequestParam String credentials,
                                               @RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer count) {
        String login = loginService.login(credentials).getLogin();
        if (login == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return dataService.getStates(page, count, login);
    }
}