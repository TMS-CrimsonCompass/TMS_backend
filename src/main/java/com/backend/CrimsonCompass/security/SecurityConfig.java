package com.backend.CrimsonCompass.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import javax.crypto.SecretKey;

@PropertySource(value = {"classpath:application-secret.properties"})

@Configuration
public class SecurityConfig {
    SecurityConstants securityConstants;
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public SecurityConfig(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(securityConstants.getSecret().getBytes());
    }

    @Bean
    public long jwtExpiration() {
        return securityConstants.getExpiration();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("127.0.0.1"); //TO-DO - AUTH_SERVICE_IP

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").permitAll().requestMatchers(HttpMethod.POST, "/api/users/sync").access((authentication, context) -> new AuthorizationDecision(ipAddressMatcher.matches(context.getRequest()))).anyRequest().authenticated());

        return http.build();
    }


}

