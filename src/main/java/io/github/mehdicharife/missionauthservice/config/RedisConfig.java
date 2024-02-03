package io.github.mehdicharife.missionauthservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisConfig {
    
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;


    @Bean
    public JedisPooled jedis() {
        JedisPooled jedis = new JedisPooled(redisHost, redisPort);
        return jedis;
    }
}
