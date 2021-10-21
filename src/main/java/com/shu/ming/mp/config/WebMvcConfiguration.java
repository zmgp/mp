package com.shu.ming.mp.config;

import com.google.common.util.concurrent.RateLimiter;
import com.shu.ming.mp.handler.RateLimiterInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @author JGod
 * @create 2021-10-21-21:59
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${requests}")
    private int requests;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         *
         */
        registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(requests)))
                .addPathPatterns("/**");
    }
}
