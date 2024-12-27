
package com.lty.bookstore.system.backend.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@AutoConfigureAfter({RedisAutoConfiguration.class})
@Slf4j
public class RedisManager<V> {
    @Resource
    private RedisTemplate<String, V> redisTemplate;

    public RedisManager() {
    }

    public long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis错误:", e);
            return false;
        }
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(Lists.newArrayList(key));
            }
        }

    }

    public boolean set(String key, V value, long time) {
        return this.set(key, value, time, TimeUnit.SECONDS);
    }

    public V get(String key) {
        return Objects.isNull(key) ? null : this.redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, V value, long time, TimeUnit unit) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, unit);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis错误:", e);
            return false;
        }
    }

    public V get(String key, Supplier<V> supplier) {
        V result = this.redisTemplate.opsForValue().get(key);
        if (result == null) {
            result = supplier.get();
            if (result != null) {
                this.set(key, result);
            }
        }

        return result;
    }

    public boolean set(String key, V value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis错误:", e);
            return false;
        }
    }

}
