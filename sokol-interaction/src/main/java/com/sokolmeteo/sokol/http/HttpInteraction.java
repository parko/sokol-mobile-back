package com.sokolmeteo.sokol.http;

import com.sokolmeteo.dao.model.Device;
import com.sokolmeteo.dao.model.Login;

import java.util.List;

public interface HttpInteraction {
    String login(Login login);

    List<Device> getPermittedDevice(String sessionId);
}
