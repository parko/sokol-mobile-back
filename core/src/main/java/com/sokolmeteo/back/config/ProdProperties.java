package com.sokolmeteo.back.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdProperties implements AppProperties {
    private String host = "localhost";
    private int port = 8001;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }
}
