package io.github.mehdicharife.missionauthservice.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.mehdicharife.missionauthservice.config.security.filters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
                authz -> authz.requestMatchers("/jwt-tokens", "/jwt-verifications", "/account-creation-requests").permitAll()
                              .requestMatchers("/accounts", "/roles").hasRole("ADMIN"))

            .csrf(AbstractHttpConfigurer::disable)
            .build();  
    }
    
}
