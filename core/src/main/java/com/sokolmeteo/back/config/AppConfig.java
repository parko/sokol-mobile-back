package com.sokolmeteo.back.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class AppConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(10000));
        factory.setMaxRequestSize(DataSize.ofKilobytes(10000));
        return factory.createMultipartConfig();
    }
}
