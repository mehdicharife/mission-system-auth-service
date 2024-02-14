package io.github.mehdicharife.missionauthservice.config.security.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import io.github.mehdicharife.missionauthservice.domain.Role;

public class RolesAuthoritiesMapper {
    
    public static List<GrantedAuthority> toAuthorities(List<Role> roles) {
        return roles.stream()           
            .map(role ->  (GrantedAuthority) () -> "ROLE_" + role.getName())
            .collect(Collectors.toList());
    }
}
