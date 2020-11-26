package com.sokolmeteo.back.api;

import com.sokolmeteo.back.service.LoginApiService;
import com.sokolmeteo.dto.LoginDto;
import com.sokolmeteo.dto.ProfileDto;
import com.sokolmeteo.dto.ValuableResponse;
import com.sokolmeteo.utils.Path.AuthPath;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginApi {
    private final LoginApiService service;

    public LoginApi(LoginApiService service) {
        this.service = service;
    }

    @PostMapping(path = AuthPath.LOGIN)
    public ValuableResponse<String> login(@RequestBody LoginDto loginDto) {
        return new ValuableResponse<>(service.login(loginDto));
    }

    @GetMapping(path = AuthPath.LOGOUT)
    public ValuableResponse<String> logout(@CookieValue(name = "JSESSIONID") String sessionId) {
        return new ValuableResponse<>(service.logout(sessionId));
    }

    @PostMapping(path = AuthPath.REGISTRATION)
    public ValuableResponse<String> register(@RequestBody ProfileDto profileDto) {
        return new ValuableResponse<>(service.register(profileDto));
    }

    @GetMapping(path = AuthPath.RECOVERY)
    public ValuableResponse<String> recover(@RequestParam String login) {
        return new ValuableResponse<>(service.recover(login));
    }
}
