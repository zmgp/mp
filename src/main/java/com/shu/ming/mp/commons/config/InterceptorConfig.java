package com.shu.ming.mp.commons.config;

import com.shu.ming.mp.commons.handler.AuthenticInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {


    AuthenticInterceptor authenticInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始配置拦截器");
        registry
                .addInterceptor(authenticInterceptor)
                .addPathPatterns("/**");
    }
}
