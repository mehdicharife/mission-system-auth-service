package io.github.mehdicharife.missionauthservice.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.dto.AccountDtoOut;
import io.github.mehdicharife.missionauthservice.dto.JwtVerificationRequest;
import io.github.mehdicharife.missionauthservice.mapper.AccountMapper;
import io.github.mehdicharife.missionauthservice.service.JwtService;


@RestController
@RequestMapping("/jwt-verifications")
public class JwtVerificationController {

    private JwtService jwtService;

    private ObjectMapper objectMapper;


    public JwtVerificationController(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }


    @PostMapping
    public ResponseEntity<JwtVerification> createJwtVerification(@RequestBody JwtVerificationRequest request) {
        Jwt jwt = new Jwt(request.getJwt());

        JwtVerification jwtVerification = this.jwtService.verifyToken(jwt);
        ResponseEntity<JwtVerification> response = new ResponseEntity<>(jwtVerification, HttpStatus.CREATED);
        if(!jwtVerification.isSuccessfull()) {
            return response;
        }

        Optional<Account> optionalAccount = this.jwtService.extractAccountFromJwt(jwt);
        if(optionalAccount.isEmpty()) {
            return response;
        }

        var headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        AccountDtoOut accountDto = AccountMapper.toDto(optionalAccount.get());
        try {
            headers.add("principal", objectMapper.writeValueAsString(accountDto));
        } catch(Exception exception) {
            // clients interested in the principal are welcome to use the endpoint /accounts in
            // order to retrieve the desired information 
        }

        return response;
    }
    
}
