package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.service.JwtService;

@RestController
@RequestMapping("/jwt-revocations")
public class JwtRevocationController {

    private JwtService jwtTokenService;

    public JwtRevocationController(JwtService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @PostMapping
    public ResponseEntity<Object> revokeJwt(@RequestBody JwtRevocation jwtRevocation) {
        this.jwtTokenService.revokeJwt(new Jwt(jwtRevocation.getJwt()));
        return new ResponseEntity<>(jwtRevocation, HttpStatus.CREATED);
    }
    
}
