package io.github.mehdicharife.missionauthservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mehdicharife.missionauthservice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
}
