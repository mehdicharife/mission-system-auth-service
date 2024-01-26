package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.JwtToken;

public interface JwtTokenService {

    JwtToken createJwtToken(String username, String password); 

    void deleteJwtToken(JwtToken jwtToken);

}
