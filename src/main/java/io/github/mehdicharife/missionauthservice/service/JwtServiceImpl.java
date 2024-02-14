package io.github.mehdicharife.missionauthservice.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.repository.JwtRevocationRepository;


@Service
public class JwtServiceImpl implements JwtService{

    private final SecretKey secretKey;

    private final long jwtLifeSpan;

    private final AccountService accountService;

    private final JwtRevocationRepository jwtRevocationRepository;

    private final ObjectMapper objectMapper;


    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.lifespan.seconds}") long jwtLifeSpan,
                          AccountService accountService,
                          JwtRevocationRepository jwtRevocationRepository,
                          ObjectMapper objectMapper) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtLifeSpan = jwtLifeSpan*1000;
        this.accountService = accountService;
        this.jwtRevocationRepository = jwtRevocationRepository;
        this.objectMapper = objectMapper;
    }
    

    public Jwt createJwtToken(String username, String password) throws BadUsernameOrPasswordException{
        Account account = this.accountService.findAccountByUsernameAndUnEncodedPassword(username, password);

        Jwt jwt =  new Jwt(createJwtStringRepresentation(account));
        jwt.setAccountId(account.getId());
        
        return jwt;
    }
    
    
    public JwtVerification verifyToken(Jwt jwt) {
        JwtVerification jwtVerification = new JwtVerification(jwt);
        boolean jwtIsAuthentic;

        try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt.getContent());

            jwtIsAuthentic = true;
        } catch(JwtException exception) {
            jwtIsAuthentic = false;
        } 

        jwtVerification.setIsSuccessfull(jwtIsAuthentic && !jwtIsRevoked(jwt));
        return jwtVerification;
    }


    public JwtRevocation revokeJwt(Jwt jwt) {
        JwtRevocation jwtRevocation = new JwtRevocation(jwt.getContent());
        this.jwtRevocationRepository.save(jwtRevocation);
        return jwtRevocation;
    }

    
    public boolean jwtIsRevoked(Jwt jwt) {
        return this.jwtRevocationRepository.existsByJwt(jwt.getContent());
    }


    public Optional<Account> extractAccountFromJwt(Jwt jwt) {
        Optional<Claims> optionalClaims = getPayload(jwt.getContent());
        if(optionalClaims.isEmpty()) {
            return Optional.empty();
        }

        Claims claims = optionalClaims.get();
        Account account = new Account();

        account.setId(claims.get("id", Long.class));

        var rolesEntry = claims.get("roles", ArrayList.class);
        List<Role> roles = objectMapper.convertValue(rolesEntry, new TypeReference<ArrayList<Role>>(){});
        account.setRoles(roles);

        return Optional.of(account);
    }


    public Optional<List<Role>> extractRolesFromJwt(Jwt jwt) throws JwtException{
        Optional<Claims> optionalClaims = getPayload(jwt.getContent());
        if(optionalClaims.isEmpty()) {
            return Optional.empty();
        }

        Claims claims = optionalClaims.get();
        var rolesEntry = claims.get("roles", ArrayList.class);
        return Optional.of(objectMapper.convertValue(rolesEntry, new TypeReference<ArrayList<Role>>(){}));
    }


    public Optional<String> extractUsername(String jwt) {
        Optional<Claims> optionalClaims = getPayload(jwt);
        if(optionalClaims.isPresent()) {
            return Optional.of(optionalClaims.get().getSubject());
        }
        return Optional.empty();
    }


    public Long extractId(String jwt) throws JwtException {
        Optional<Claims> optionalClaims = getPayload(jwt); 
        if(optionalClaims.isEmpty()) {
            return null;
        }

        return optionalClaims.get().get("id", Long.class);    
    }


    public Optional<Claims> getPayload(String jwt) {
        try {
            Claims claims =  Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            return Optional.of(claims);
        } catch(JwtException exception) {
            return Optional.empty();
        }
    }


    private String createJwtStringRepresentation(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", account.getRoles());
        claims.put("id", account.getId());
        
        String jwt = Jwts.builder()
            .json(new JacksonSerializer<>(objectMapper))
            .claims(claims)
            .subject(account.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.jwtLifeSpan))
            .signWith(this.secretKey)
            .compact();

        return jwt;
    }


}
