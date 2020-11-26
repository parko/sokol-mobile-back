package com.sokolmeteo.sokol.config;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Getter
public class ProdConnectionProps implements ConnectionProps {
    private final String tcpHost = "localhost";
    private final int tcpPort = 8001;
    private final String httpHost = "localhost:8080";
}
