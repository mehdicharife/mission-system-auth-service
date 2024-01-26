package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.Role;

public interface RoleService {

    Role createRole(Role role);

    Role deleteRole(Role role);
    
}
