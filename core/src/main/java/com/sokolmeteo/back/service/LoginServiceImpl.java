package com.sokolmeteo.back.service;

import com.sokolmeteo.dao.model.AuthSession;
import com.sokolmeteo.dao.model.Device;
import com.sokolmeteo.dao.model.Login;
import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.SokolHttpException;
import com.sokolmeteo.utils.exception.NoDevicePermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Service
@EnableCaching
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final HttpInteraction httpInteraction;

    public LoginServiceImpl(HttpInteraction httpInteraction) {
        this.httpInteraction = httpInteraction;
    }

    @Override
    @Cacheable(value = "login", key = "#credentials")
    public AuthSession login(String credentials) {
        Login login = decode(credentials);
        String sessionId = httpInteraction.login(login);
        logger.info("Authorized by " + login.getLogin());
        return new AuthSession(login.getLogin(), sessionId);
    }

    @Override
    @Cacheable(value = "permission", key = "#credentials.concat('-').concat(#imei)")
    public AuthSession checkPermission(String credentials, String imei) {
        Login login = decode(credentials);
        String sessionId = httpInteraction.login(login);
        List<Device> permittedDevices = httpInteraction.getPermittedDevice(sessionId);
        for (Device device : permittedDevices) {
            if (device.getImei().equals(imei)) {
                logger.info("Permitted: {} - {}", login.getLogin(), imei);
                return new AuthSession(login.getLogin(), sessionId);
            }

        }
        throw new NoDevicePermissionException("Нет доступа к устройству");
    }

    private Login decode(String credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(credentials));
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            return new Login(tokenizer.nextToken(), tokenizer.nextToken());
        } catch (Exception e) {
            logger.warn("Exception on credentials decoding: " + e);
            throw new SokolHttpException("Неверная строка credentials");
        }
    }
}
