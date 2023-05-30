package cn.eleven.lab02.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-05-11 16:56
 */

@Component
public class RedisRepo {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String get(String key) {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);
        return ops.get();
    }

    public void save(String key, String str) {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);
        ops.set(str);
    }

    public void saveTimeout(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.boundValueOps(key).setIfAbsent(value, timeout, unit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public long expire(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
