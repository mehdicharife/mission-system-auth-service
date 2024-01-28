package io.github.mehdicharife.missionauthservice.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.domain.JwtTokenVerification;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;


@Service
public class JwtTokenServiceImpl implements JwtTokenService{

    private final SecretKey secretKey;

    private final long jwtLifeSpan;

    private final AccountService accountService;


    public JwtTokenServiceImpl(@Value("${jwt.secret}") String secret,
                               @Value("${jwt.lifespan.seconds}") long jwtLifeSpan,
                               AccountService accountService) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtLifeSpan = jwtLifeSpan;
        this.accountService = accountService;
    }
    

    public JwtToken createJwtToken(String username, String password) throws BadUsernameOrPasswordException{
        Account account = this.accountService.findAccountByUsernameAndUnEncodedPassword(username, password);

        return new JwtToken(createJwtStringRepresentation(account));
    }

    
    public JwtTokenVerification verifyToken(JwtToken jwtToken) {
        JwtTokenVerification jwtTokenVerification = new JwtTokenVerification(jwtToken);

        try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken.getContent());

            jwtTokenVerification.setIsSuccessfull(true);
        } catch(JwtException exception) {
            jwtTokenVerification.setIsSuccessfull(false);
        } 

        return jwtTokenVerification;
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
