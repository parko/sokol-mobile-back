package com.sokolmeteo.sokol.config;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Component
//@Profile("dev")
@Getter
public class DevConnectionProps implements ConnectionProps {
    private final String tcpHost = "185.27.193.112";
    private final int tcpPort = 8002;
    private final String httpHost = "http://185.27.193.112:8080/test";
}
