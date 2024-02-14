package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.service.JwtService;


@RestController
@RequestMapping("/jwt-verifications")
public class JwtTokenValidationController {

    private JwtService jwtTokenService;

    public JwtTokenValidationController(JwtService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public ResponseEntity<Object> createJwtTokenVerification(@RequestHeader(HttpHeaders.AUTHORIZATION) String preFixedJwt) {
        String prefix = "Bearer";
        if(!preFixedJwt.startsWith(prefix)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String rawJwt = preFixedJwt.substring(prefix.length());
        JwtVerification jwtTokenVerification = this.jwtTokenService.verifyToken(new Jwt(rawJwt));

        return new ResponseEntity<>(jwtTokenVerification, HttpStatus.CREATED);
    }
    
}
