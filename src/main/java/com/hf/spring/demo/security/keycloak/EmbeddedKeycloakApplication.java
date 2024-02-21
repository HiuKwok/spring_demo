package com.hf.spring.demo.security.keycloak;

import org.keycloak.Config;
import org.keycloak.exportimport.ExportImportManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.NoSuchElementException;

public class EmbeddedKeycloakApplication extends KeycloakApplication {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddedKeycloakApplication.class);

    static KeycloakServerProperties keycloakServerProperties;

    protected void loadConfig() {
        JsonConfigProviderFactory factory = new RegularJsonConfigProviderFactory();
        Config.init(factory.create()
                .orElseThrow(() -> new NoSuchElementException("No value present")));
    }

    @Override
    protected ExportImportManager bootstrap() {
        final ExportImportManager exportImportManager = super.bootstrap();
        createMasterRealmAdminUser();
        createHfRealm();
        return exportImportManager;
    }

    private void createMasterRealmAdminUser() {
        KeycloakSession session = getSessionFactory().create();
        ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
        KeycloakServerProperties.AdminUser admin = keycloakServerProperties.getAdminUser();
        try {
            session.getTransactionManager().begin();
            applianceBootstrap.createMasterRealmUser(admin.getUsername(), admin.getPassword());
            session.getTransactionManager().commit();
        } catch (Exception e) {
            logger.warn("Couldn't create Keycloak master admin user: {}", e.getMessage());
            session.getTransactionManager()
                    .rollback();
        }
        session.close();
    }

    private void createHfRealm() {
        KeycloakSession session = getSessionFactory().create();
        try {
            session.getTransactionManager().begin();
            RealmManager manager = new RealmManager(session);
            Resource lessonRealmImportFile = new ClassPathResource(
                    keycloakServerProperties.getContextPath());
            manager.importRealm(JsonSerialization.readValue(lessonRealmImportFile.getInputStream(),
                    RealmRepresentation.class));
            session.getTransactionManager().commit();
        } catch (Exception e) {
            logger.warn("Failed to import Realm json file: {}", e.getMessage());
            session.getTransactionManager().rollback();
        }
        session.close();
    }

}
