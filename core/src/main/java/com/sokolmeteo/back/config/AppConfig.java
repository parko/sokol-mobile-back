package com.sokolmeteo.back.config;

import com.sokolmeteo.sokol.http.HttpInteraction;
import com.sokolmeteo.sokol.http.HttpInteractionImpl;
import com.sokolmeteo.sokol.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    private AppProperties properties;

    @Autowired
    public void setProperties(AppProperties properties) {
        this.properties = properties;
    }

    @Bean
    public TcpClient tcpClient() {
        return new TcpClient(properties.getTcpHost(), properties.getTcpPort());
    }

    @Bean
    public HttpInteraction httpInteraction() {
        return new HttpInteractionImpl(properties.getHttpHost());
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(10000));
        factory.setMaxRequestSize(DataSize.ofKilobytes(10000));
        return factory.createMultipartConfig();
    }
}
