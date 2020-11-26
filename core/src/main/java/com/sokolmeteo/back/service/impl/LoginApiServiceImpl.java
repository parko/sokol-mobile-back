package com.sokolmeteo.back.service.impl;

import com.sokolmeteo.back.service.LoginApiService;
import com.sokolmeteo.dto.LoginDto;
import com.sokolmeteo.dto.ProfileDto;
import com.sokolmeteo.sokol.http.model.Login;
import com.sokolmeteo.sokol.http.model.Profile;
import com.sokolmeteo.sokol.http.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class LoginApiServiceImpl implements LoginApiService {
    private final AuthService authService;

    public LoginApiServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String login(LoginDto loginDto) {
        return authService.login(new Login(loginDto.getLogin(), loginDto.getPassword()));
    }

    @Override
    public String logout(String sessionId) {
        return authService.logout(sessionId);
    }

    @Override
    public String register(ProfileDto profileDto) {
        return authService.register(new Profile(profileDto.getEmail(), profileDto.getPhone(),
                profileDto.getPassword(), profileDto.getFields()));
    }

    @Override
    public String recover(String login) {
        return authService.recover(login);
    }
}
