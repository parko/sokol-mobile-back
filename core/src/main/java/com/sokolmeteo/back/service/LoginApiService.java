package com.sokolmeteo.back.service;

import com.sokolmeteo.dto.LoginDto;
import com.sokolmeteo.dto.ProfileDto;

public interface LoginApiService {
    String login(LoginDto loginDto);

    String logout(String sessionId);

    String register(ProfileDto profileDto);

    String recover(String login);
}
