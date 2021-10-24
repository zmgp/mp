package com.shu.ming.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;


@MapperScan("com.shu.ming.mp.modules.login.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
public class MpApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MpApplication.class, args);
    }
}
