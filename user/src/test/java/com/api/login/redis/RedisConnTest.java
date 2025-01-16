package com.api.login.redis;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class RedisConnTest {

    @InjectMocks
    private StringRedisTemplate redisTemplate;

    @Test
    void testDirectRedisConnection() {
        String testKey = "test-key";
        String testValue = "test-value";

        redisTemplate.opsForValue().set(testKey, testValue);

        String retrievedValue = redisTemplate.opsForValue().get(testKey);

        assertNotNull(retrievedValue, "Redis에서 값을 읽어오지 못했습니다.");
        assertEquals(testValue, retrievedValue, "Redis 연결 테스트 실패: 값이 일치하지 않음.");
    }
}
