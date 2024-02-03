package io.github.mehdicharife.missionauthservice.repository;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;

public interface JwtRevocationRepository {

    void save(JwtRevocation jwtRevocation);

    boolean existsByJwt(String jwt);
    
}
