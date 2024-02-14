package io.github.mehdicharife.missionauthservice.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.mehdicharife.missionauthservice.config.security.filters.JwtBearerFilter;
import io.github.mehdicharife.missionauthservice.config.security.filters.JwtCookieFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtBearerFilter jwtBearerFilter;

    @Autowired
    private JwtCookieFilter jwtCookieFilter;

    @Bean
    public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .addFilterBefore(jwtBearerFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtCookieFilter, JwtBearerFilter.class)
            .authorizeHttpRequests(
                authz -> authz.requestMatchers("/jwt-tokens", "/jwt-verifications", "/account-creation-requests", "/jwt-revocations").permitAll()
                              .requestMatchers("/roles").hasRole("ADMIN")
                              .requestMatchers("/**").permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .build();  
    }
    
}
