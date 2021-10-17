package com.shu.ming.mp;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@MapperScan("com.shu.ming.mp.modules.login.mapper")
@SpringBootApplication
@Slf4j
public class MpApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MpApplication.class, args);
    }

}
