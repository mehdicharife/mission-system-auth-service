package io.github.mehdicharife.missionauthservice.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
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


    
    public JwtVerification verifyToken(Jwt jwtToken) {
        JwtVerification jwtTokenVerification = new JwtVerification(jwtToken);
        boolean jwtIsAuthentic;

        try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken.getContent());

            jwtIsAuthentic = true;
        } catch(JwtException exception) {
            jwtIsAuthentic = false;
        } 

        jwtTokenVerification.setIsSuccessfull(jwtIsAuthentic && !jwtIsRevoked(jwtToken));
        return jwtTokenVerification;
    }

    //TODO: add exceptions for when the jwt is bad (e.g. bad signature, expired)
    public JwtRevocation revokeJwt(Jwt jwtToken) {
        JwtRevocation jwtRevocation = new JwtRevocation(jwtToken.getContent());
        this.jwtRevocationRepository.save(jwtRevocation);
        return jwtRevocation;
    }

    
    public boolean jwtIsRevoked(Jwt jwtToken) {
        return this.jwtRevocationRepository.existsByJwt(jwtToken.getContent());
    }


    @SuppressWarnings("unchecked")
    public List<Role> getRolesFromJwt(Jwt jwt) throws JwtException{
        
        var claimsMap =  Jwts.parser()
                   .json(new JacksonDeserializer<>(objectMapper))
                   .verifyWith(secretKey)
                   .build()
                   .parseSignedClaims(jwt.getContent())
                   .getPayload();


        var rolesEntry = claimsMap.get("roles", ArrayList.class);
        return objectMapper.convertValue(rolesEntry, new TypeReference<ArrayList<Role>>(){});
    }

    public String extractUsername(String jwt) throws JwtException{
        String username = Jwts
                            .parser()
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(jwt)
                            .getPayload()
                            .getSubject();
        return username;    
    }

    public Long extractId(String jwt) throws JwtException {
        return Jwts.parser()
            .json(new JacksonDeserializer<>(objectMapper))
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(jwt)
            .getPayload()
            .get("id", Long.class);
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
