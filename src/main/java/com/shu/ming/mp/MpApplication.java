package com.shu.ming.mp;

import com.shu.ming.mp.commons.config.ThreadPoolConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@MapperScan("com.shu.ming.mp.modules.login.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@EnableSwagger2
@EnableConfigurationProperties({ThreadPoolConfig.class})
public class MpApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MpApplication.class, args);
    }
}
