package com.sokolmeteo.back.controller;

import com.sokolmeteo.back.service.DataService;
import com.sokolmeteo.dao.model.Details;
import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.HttpInteraction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/data")
public class DataController {
    private final HttpInteraction httpInteraction;
    private final DataService dataService;

    public DataController(HttpInteraction httpInteraction, DataService dataService) {
        this.httpInteraction = httpInteraction;
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Long> uploadFile(@RequestParam String credentials, @RequestBody MultipartFile file) {
        return authorize(credentials) ? dataService.sendData(file) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<String> getState(@RequestParam String credentials, @PathVariable String dataId) {
        return authorize(credentials) ? dataService.getState(dataId) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{dataId}/details")
    public ResponseEntity<Details> getDetails(@RequestParam String credentials, @PathVariable String dataId) {
        return authorize(credentials) ? dataService.getDetails(dataId) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private boolean authorize(String credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(credentials));
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            httpInteraction.login(new Login(tokenizer.nextToken(), tokenizer.nextToken()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
