package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;

import java.util.List;

import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.jsonwebtoken.JwtException;

public interface JwtService  {

    Jwt createJwtToken(String username, String password) throws BadUsernameOrPasswordException;
    
    JwtVerification verifyToken(Jwt jwtToken);

    JwtRevocation revokeJwt(Jwt jwtToken);

    String extractUsername(String jwt) throws JwtException;

    public List<Role> getRolesFromJwt(Jwt jwt) throws JwtException;

    public Long extractId(String jwt) throws JwtException;

}
