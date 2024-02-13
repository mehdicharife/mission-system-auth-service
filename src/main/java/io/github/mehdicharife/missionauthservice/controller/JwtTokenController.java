package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.dto.CreateJwtTokenRequest;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.service.JwtTokenService;

@RestController
@CrossOrigin
@RequestMapping("/jwt-tokens")
public class JwtTokenController {

    private JwtTokenService jwtTokenService;

    private ObjectMapper objectMapper;

    public JwtTokenController(JwtTokenService jwtTokenService, ObjectMapper objectMapper) {
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = objectMapper;
    }

    
    @PostMapping
    public ResponseEntity<Object> createJwtToken(@RequestBody CreateJwtTokenRequest createJwtTokenRequest) {

        try {
            JwtToken jwtToken = jwtTokenService.createJwtToken(
                createJwtTokenRequest.getUsername(),
                createJwtTokenRequest.getPassword()
            );

            ResponseCookie jwtCookie = ResponseCookie
                                            .from("" + jwtToken.getAccountId(), jwtToken.getContent())
                                            .domain("auth-service.mission-system.com")
                                            .httpOnly(true)
                                            .sameSite("Strict")
                                            .build();

            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("SET-COOKIE", jwtCookie.toString())
                    .body(objectMapper.createObjectNode().put("accountId", jwtToken.getAccountId() + ""));
            
        } catch(BadUsernameOrPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
            
        } 

    }


    
}
