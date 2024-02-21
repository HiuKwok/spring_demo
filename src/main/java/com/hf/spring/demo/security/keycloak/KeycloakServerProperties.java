package com.hf.spring.demo.security.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Simple POJO to set the context Path, adminUser and realm definition.
 * Ref: https://www.baeldung.com/keycloak-embedded-in-spring-boot-app
 */
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {
    String contextPath = "/auth";
    String realmImportFile = "hf-realm.json";

    AdminUser adminUser = new AdminUser();

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getRealmImportFile() {
        return realmImportFile;
    }

    public void setRealmImportFile(String realmImportFile) {
        this.realmImportFile = realmImportFile;
    }

    public static class AdminUser {
        String username = "admin";
        String password = " admin";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
