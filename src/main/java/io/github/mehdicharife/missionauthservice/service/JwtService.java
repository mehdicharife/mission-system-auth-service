package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;

import java.util.List;
import java.util.Optional;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.jsonwebtoken.JwtException;

public interface JwtService  {

    Jwt createJwtToken(String username, String password) throws BadUsernameOrPasswordException;
    
    JwtVerification verifyToken(Jwt jwtToken);

    JwtRevocation revokeJwt(Jwt jwtToken);

    Optional<Account> extractAccountFromJwt(Jwt jwt);

    Optional<String> extractUsername(String jwt) throws JwtException;

    Optional<List<Role>> extractRolesFromJwt(Jwt jwt) throws JwtException;

    Long extractId(String jwt) throws JwtException;

}
