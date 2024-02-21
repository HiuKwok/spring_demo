package com.hf.spring.demo;

import com.hf.spring.demo.security.keycloak.KeycloakServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

// General use, exclude liquibase for JWT use-case.
@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties(KeycloakServerProperties.class)
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(
            ServerProperties serverProperties, KeycloakServerProperties keycloakServerProperties) {
        return (evt) -> {
            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();
            logger.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak",
                    port, keycloakContextPath);
        };
    }
}
