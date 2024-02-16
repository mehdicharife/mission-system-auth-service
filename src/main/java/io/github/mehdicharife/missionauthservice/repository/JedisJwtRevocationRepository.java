package io.github.mehdicharife.missionauthservice.repository;

import org.springframework.stereotype.Repository;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisDataException;

@Repository
public class JedisJwtRevocationRepository implements JwtRevocationRepository {
    
    private final JedisPooled jedis;

    private String JWT_REVOCATIONS_SET_KEY = "jwts:revoked";

    
    public JedisJwtRevocationRepository(JedisPooled jedis) {
        this.jedis = jedis;
        try {
            jedis.bfReserve(JWT_REVOCATIONS_SET_KEY, 0.01, 1000);
        } catch(JedisDataException exception) {
            System.out.println("The key is already reserved");
        }
    }


    public void save(JwtRevocation jwtRevocation) {
        jedis.bfAdd(JWT_REVOCATIONS_SET_KEY, jwtRevocation.getJwt());
    }


    public boolean existsByJwt(String jwt) {
        return jedis.bfExists(JWT_REVOCATIONS_SET_KEY, jwt);
    }

}
