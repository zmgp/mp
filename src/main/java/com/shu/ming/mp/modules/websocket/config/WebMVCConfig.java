package com.shu.ming.mp.modules.websocket.config;

import com.google.common.util.concurrent.RateLimiter;
import com.shu.ming.mp.commons.handler.RateLimiterInterceptor;
import com.shu.ming.mp.modules.websocket.interceptor.OnLineInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author JGod
 * @create 2021-10-17-17:22
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer  {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         *
         */
        registry.addInterceptor(new OnLineInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        ;
    }


}
