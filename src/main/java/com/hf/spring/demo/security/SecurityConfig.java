package com.hf.spring.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

// Protect Access with an OAuth2 Access Token
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests((authorize) -> {
//                    authorize.anyRequest().authenticated()
//                })
//                .oauth2ResourceServer((oauth2) -> {
//                    oauth2.jwt(Customizer.withDefaults())
//                });
//        return http.build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return JwtDecoders.fromIssuerLocation("https://my-auth-server.com");
//    }


    // From random JWT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }

    private RSAPublicKey publicKey () {
        return null;
    }

}
