package com.backend.CrimsonCompass.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {
    JwtProperties jwtProperties;


    public SecurityConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Bean
    public long jwtExpiration() {
        return jwtProperties.getExpiration();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("127.0.0.1"); //TO-DO - AUTH_SERVICE_IP

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").permitAll().requestMatchers(HttpMethod.POST, "/api/users/sync").access((authentication, context) -> new AuthorizationDecision(ipAddressMatcher.matches(context.getRequest()))).anyRequest().authenticated());

        return http.build();
    }


}

