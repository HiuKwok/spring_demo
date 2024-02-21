package com.hf.spring.demo.security.keycloak;

import org.keycloak.common.util.SystemEnvProperties;
import org.keycloak.services.util.JsonConfigProviderFactory;

import java.util.Properties;

public class RegularJsonConfigProviderFactory extends JsonConfigProviderFactory {
    @Override
    protected Properties getProperties() {
        return new SystemEnvProperties(System.getenv());
    }
}
