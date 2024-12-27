package com.lty.bookstore.system.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * RedissonConfig.
 *
 * @Version 1.0
 */
@Slf4j
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        singleServerConfig.setPassword(password);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("RedissonClient init成功-》》》》 address={}", "redis://" + host + ":" + port);
        return redissonClient;
    }

    @Bean
    public RedissonService redissonService(RedissonClient redissonClient) {
        return new RedissonService(redissonClient);
    }


    @Slf4j
    public static class RedissonService {

        private RedissonClient redissonClient;

        public RedissonService(RedissonClient redissonClient) {
            this.redissonClient = redissonClient;
        }

        public void lock(String lockName, Runnable service) {
            Assert.hasText(lockName, "redisson分布式锁key不能为空");
            Assert.notNull(service, "业务逻辑不能为null");
            // 使用redisson分布式锁来完成.
            RLock redisLock = redissonClient.getLock(lockName);
            try {
                if (!redisLock.tryLock()) {
                    log.warn("获取锁/{}失败,已经有线程在执行当前操作.....", lockName);
                    return;
                }
                service.run();
            } finally {
                if (redisLock.isHeldByCurrentThread()) {
                    redisLock.unlock();
                }
            }
        }
    }
}
