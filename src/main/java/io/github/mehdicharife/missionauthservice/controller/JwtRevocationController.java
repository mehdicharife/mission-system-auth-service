package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.service.JwtTokenService;

@RestController
@RequestMapping("/jwt-revocations")
public class JwtRevocationController {

    private JwtTokenService jwtTokenService;

    public JwtRevocationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @PostMapping
    public ResponseEntity<Object> revokeJwt(@RequestBody JwtRevocation jwtRevocation) {
        this.jwtTokenService.revokeJwt(new JwtToken(jwtRevocation.getJwt()));
        return new ResponseEntity<>(jwtRevocation, HttpStatus.CREATED);
    }
    
}
