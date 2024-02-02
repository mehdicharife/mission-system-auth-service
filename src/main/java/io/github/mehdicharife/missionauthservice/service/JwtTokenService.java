package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.domain.JwtTokenVerification;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.jsonwebtoken.JwtException;

public interface JwtTokenService  {

    JwtToken createJwtToken(String username, String password) throws BadUsernameOrPasswordException;
    
    JwtTokenVerification verifyToken(JwtToken jwtToken);

    String extractUsername(String jwt) throws JwtException;

}
