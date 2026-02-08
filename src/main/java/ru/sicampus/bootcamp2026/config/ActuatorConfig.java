package ru.sicampus.bootcamp2026.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;

@Configuration
public class ActuatorConfig {

    @Bean
    public InMemoryHttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }
}
