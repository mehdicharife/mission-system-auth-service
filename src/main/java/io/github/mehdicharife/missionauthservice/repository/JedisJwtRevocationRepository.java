package io.github.mehdicharife.missionauthservice.repository;

import org.springframework.stereotype.Repository;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import redis.clients.jedis.JedisPooled;

@Repository
public class JedisJwtRevocationRepository implements JwtRevocationRepository {
    
    private final JedisPooled jedis;

    private String JWT_REVOCATIONS_SET_KEY = "jwts:revoked";

    
    public JedisJwtRevocationRepository(JedisPooled jedis) {
        this.jedis = jedis;
        //jedis.bfInfo(JWT_REVOCATIONS_SET_KEY)
        //jedis.bfReserve(JWT_REVOCATIONS_SET_KEY, 0.01, 1000);
    }


    public void save(JwtRevocation jwtRevocation) {
        jedis.bfAdd(JWT_REVOCATIONS_SET_KEY, jwtRevocation.getJwt());
    }


    public boolean existsByJwt(String jwt) {
        return jedis.bfExists(JWT_REVOCATIONS_SET_KEY, jwt);
    }

}
