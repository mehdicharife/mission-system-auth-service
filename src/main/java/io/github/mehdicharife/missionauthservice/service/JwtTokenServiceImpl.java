package io.github.mehdicharife.missionauthservice.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.domain.JwtTokenVerification;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.repository.JwtRevocationRepository;


@Service
public class JwtTokenServiceImpl implements JwtTokenService{

    private final SecretKey secretKey;

    private final long jwtLifeSpan;

    private final AccountService accountService;

    private final JwtRevocationRepository jwtRevocationRepository;


    public JwtTokenServiceImpl(@Value("${jwt.secret}") String secret,
                               @Value("${jwt.lifespan.seconds}") long jwtLifeSpan,
                               AccountService accountService,
                               JwtRevocationRepository jwtRevocationRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtLifeSpan = jwtLifeSpan*1000;
        this.accountService = accountService;
        this.jwtRevocationRepository = jwtRevocationRepository;
    }
    

    public JwtToken createJwtToken(String username, String password) throws BadUsernameOrPasswordException{
        Account account = this.accountService.findAccountByUsernameAndUnEncodedPassword(username, password);

        return new JwtToken(createJwtStringRepresentation(account));
    }


    
    public JwtTokenVerification verifyToken(JwtToken jwtToken) {
        JwtTokenVerification jwtTokenVerification = new JwtTokenVerification(jwtToken);
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
    public JwtRevocation revokeJwt(JwtToken jwtToken) {
        JwtRevocation jwtRevocation = new JwtRevocation(jwtToken.getContent());
        this.jwtRevocationRepository.save(jwtRevocation);
        return jwtRevocation;
    }

    
    public boolean jwtIsRevoked(JwtToken jwtToken) {
        return this.jwtRevocationRepository.existsByJwt(jwtToken.getContent());
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




    private String createJwtStringRepresentation(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", account.getRoles().toString());
        
        String jwt = Jwts.builder()
            .claims(claims)
            .subject(account.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.jwtLifeSpan))
            .signWith(this.secretKey)
            .compact();

        return jwt;
    }


}
