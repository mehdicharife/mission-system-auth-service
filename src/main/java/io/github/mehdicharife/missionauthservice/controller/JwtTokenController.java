package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.dto.CreateJwtTokenRequest;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.service.JwtTokenService;

@RestController
@RequestMapping("/jwt-tokens")
public class JwtTokenController {

    private JwtTokenService jwtTokenService;

    public JwtTokenController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public ResponseEntity<Object> createJwtToken(@RequestBody CreateJwtTokenRequest createJwtTokenRequest) {
        try {
            JwtToken jwtToken = jwtTokenService.createJwtToken(
                createJwtTokenRequest.getUsername(),
                createJwtTokenRequest.getPassword()
            );

            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
            
        } catch(BadUsernameOrPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        } 

    }


    
}
