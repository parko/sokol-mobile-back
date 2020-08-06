package com.sokolmeteo.back.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevProperties implements AppProperties {
    private final String host = "185.27.193.112";
    private final int port = 8002;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }
}
