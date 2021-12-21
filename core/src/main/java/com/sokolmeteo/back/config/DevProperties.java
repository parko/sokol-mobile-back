package com.sokolmeteo.back.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Component
//@Profile("dev")
public class DevProperties implements AppProperties {
    private final String tcpHost = "185.27.193.112";
    private final int tcpPort = 8002;
    private final String httpHost = "https://sokolmeteo.com";

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
        return httpHost;
    }
}
