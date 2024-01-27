package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.exception.InvalidPasswordException;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;

public interface JwtTokenService {

    JwtToken createJwtToken(String username, String password) throws UsernameAlreadyExistsException, InvalidPasswordException;
    
    boolean isJwtValid(JwtToken jwtToken);

    void deleteJwtToken(JwtToken jwtToken);

}
