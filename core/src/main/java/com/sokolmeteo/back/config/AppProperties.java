package com.sokolmeteo.back.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sokol")
@Getter @Setter
public class AppProperties {
    private String host;
    private int port;
}
