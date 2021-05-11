package com.example.todolistapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {

    public static final String ASYNC_THREAD_POOL_EXECUTOR = "threadPoolTaskExecutor";

    @Value("${async.max.thread.pool}")
    private int maxPoolSize;

    @Value("${async.core.thread.pool}")
    private int corePoolSize;

    @Value("${async.max.queue.capacity}")
    private int maxQueueCapacity;

    @Bean(ASYNC_THREAD_POOL_EXECUTOR)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setQueueCapacity(maxQueueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix("async-thread-pool-");
        return threadPoolTaskExecutor;
    }
}
