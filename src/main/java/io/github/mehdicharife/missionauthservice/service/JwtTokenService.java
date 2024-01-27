package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.domain.JwtTokenVerification;
import io.github.mehdicharife.missionauthservice.exception.InvalidPasswordException;
import io.github.mehdicharife.missionauthservice.exception.UsernameDoesntExistException;

public interface JwtTokenService {

    JwtToken createJwtToken(String username, String password) throws UsernameDoesntExistException, InvalidPasswordException;
    
    JwtTokenVerification verifyToken(JwtToken jwtToken);

}
