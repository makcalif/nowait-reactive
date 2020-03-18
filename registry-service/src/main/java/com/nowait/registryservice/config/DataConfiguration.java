package com.nowait.registryservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {

    @ConfigurationProperties(prefix = "")
    @Bean
    public Data getData() {
        return new Data();
    }
}
