package com.api.login.common.util.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${user.jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeRefreshToken(String loginId, String refreshToken) {
        redisTemplate.opsForValue().set(loginId, refreshToken, refreshTokenExpiration, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String loginId) {
        return redisTemplate.opsForValue().get(loginId);
    }

    public void deleteRefreshToken(String loginId) {
        redisTemplate.delete(loginId);
    }

    public boolean isRefreshTokenValid(String loginId, String refreshToken) {
        String storedToken = getRefreshToken(loginId);
        return storedToken != null && storedToken.equals(refreshToken);
    }
}
