package io.github.mehdicharife.missionauthservice.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class config {

    @Bean
    public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(
                authz -> authz.requestMatchers("/jwt-tokens").permitAll()
                              .requestMatchers("/accounts", "/roles").hasRole("ADMIN"))

            .csrf().disable()
            .build();  
    }
    
}
