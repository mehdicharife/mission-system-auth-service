package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.dto.CreateJwtTokenRequest;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.service.JwtService;



@RestController
@CrossOrigin
@RequestMapping("/jwt-tokens")
public class JwtController {

    private final JwtService jwtService;

    private final String JWT_COOKIE_NAME;

    private ObjectMapper objectMapper;


    public JwtController(JwtService jwtService, @Value("${cookies.jwt.name}") String JWT_COOKIE_NAME,  ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.JWT_COOKIE_NAME = JWT_COOKIE_NAME;
        this.objectMapper = objectMapper;
    }

    
    @PostMapping
    public ResponseEntity<Object> createJwtToken(@RequestBody CreateJwtTokenRequest createJwtTokenRequest) {

        try {
            Jwt jwt = jwtService.createJwtToken(
                createJwtTokenRequest.getUsername(),
                createJwtTokenRequest.getPassword()
            );

            ResponseCookie jwtCookie = ResponseCookie
                                            .from(JWT_COOKIE_NAME, jwt.getContent())
                                            .domain("auth-service.mission-system.com")
                                            .httpOnly(true)
                                            .sameSite("Strict")
                                            .build();

            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("SET-COOKIE", jwtCookie.toString())
                    .body(objectMapper.createObjectNode().put("accountId", jwt.getAccountId() + ""));
            
        } catch(BadUsernameOrPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
            
        } 

    }


    
}
