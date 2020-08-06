package com.sokolmeteo.back.config;

import com.sokolmeteo.sokol.tcp.TcpClient;
import com.sokolmeteo.sokol.tcp.TcpClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class AppConfig {
    private AppProperties properties;

    @Autowired
    public void setProperties(AppProperties properties) {
        this.properties = properties;
    }

    @Bean
    public TcpClient tcpClient() {
        return new TcpClientImpl(properties.getHost(), properties.getPort());
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(10000));
        factory.setMaxRequestSize(DataSize.ofKilobytes(10000));
        return factory.createMultipartConfig();
    }
}
