package io.github.mehdicharife.missionauthservice.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.JwtToken;
import io.github.mehdicharife.missionauthservice.exception.InvalidPasswordException;
import io.github.mehdicharife.missionauthservice.exception.UsernameDoesntExistException;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;


@Service
public class JwtTokenServiceImpl {

    private final String secret;

    private final long jwtLifeSpan;

    private AccountRepository accountRepository;


    public JwtTokenServiceImpl(@Value("${jwt.secret}") String secret,
                               @Value("${jwt.lifespan.seconds}") long jwtLifeSpan,
                               AccountRepository accountRepository) {
        this.secret = secret;
        this.jwtLifeSpan = jwtLifeSpan;
        this.accountRepository = accountRepository;
    }
    
    

    public JwtToken createJwtToken(String username, String password) throws UsernameDoesntExistException, InvalidPasswordException{
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalAccount.isEmpty()) {
            throw new UsernameDoesntExistException(username);
        } 

        Account account = optionalAccount.get();

        if(!password.equals(account.getPassword())) {
            throw new InvalidPasswordException();
        }

        return new JwtToken(createJwtStringRepresentation(account));

    }

    private String createJwtStringRepresentation(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", account.getRoles().toString());
        

        String jwt = Jwts.builder()
            .claims(claims)
            .subject(account.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.jwtLifeSpan))
            .signWith(Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8)))
            .compact();

        return jwt;
    }
}
