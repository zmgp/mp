package com.shu.ming.mp.commons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author JGod
 * @create 2021-11-13-13:54
 */
@ConfigurationProperties(prefix = "task.poll")
@Data
public class ThreadPoolConfig {
        private int corePoolSize;           // 核心线程数（默认线程数）
        private int maxPoolSize;            // 最大线程数
        private int keepAliveTime;          // 允许线程空闲时间（单位：默认为秒）
        private int queueCapacity;          // 缓冲队列数
        private String threadNamePrefix;   // 线程池名前缀


        /**
         * 默认异步线程池
         * @return
         */
        @Bean
        public ThreadPoolTaskExecutor taskExecutor() {
                ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
                pool.setThreadNamePrefix(threadNamePrefix);
                pool.setCorePoolSize(corePoolSize);
                pool.setMaxPoolSize(maxPoolSize);
                pool.setKeepAliveSeconds(keepAliveTime);

                pool.setQueueCapacity(queueCapacity);
                // 直接在execute方法的调用线程中运行
                pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                // 等待所有任务结束后再关闭线程池
                pool.setWaitForTasksToCompleteOnShutdown(true);
                // 初始化
                pool.initialize();
                return pool;
        }
}
