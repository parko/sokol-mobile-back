package com.sokolmeteo.back.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("prod")
public class ProdProperties implements AppProperties {
    private final String tcpHost = "localhost";
    private final int tcpPort = 8001;

    @Override
    public String getTcpHost() {
        return tcpHost;
    }

    @Override
    public int getTcpPort() {
        return tcpPort;
    }

    @Override
    public String getHttpHost() {
        return String.format("http://%s:%d", tcpHost, 8080);
    }
}
