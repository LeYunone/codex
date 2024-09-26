package com.leyunone.codex.system;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/18 14:49
 */
@Configuration
@EnableConfigurationProperties(BotThreadProperties.class)
public class ThreadPoolConfig {

    @Bean("botPool")
    public ThreadPoolExecutor processorThreadPool(BotThreadProperties threadProperties) {
        return new ThreadPoolExecutor(threadProperties.getCorePoolSize(), threadProperties.getMaxPoolSize(),
                threadProperties.getKeepAliveSeconds(), TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(threadProperties.getQueueCapacity()), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }
}
