package com.shu.ming.mp.modules.websocket.config;

import com.maxmind.geoip2.DatabaseReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author JGod
 * @create 2021-10-18-18:02
 */
@Configuration
@Slf4j
public class IpDatabaseConfig {

    @Value("${ip-database-path}")
    private String path;

    @Bean(destroyMethod="close")
    public DatabaseReader databaseReader(){
        log.info("开始配置ip地址映射");
        try {
            return new DatabaseReader.Builder(new File(path)).build();
        } catch (IOException e) {
            log.error("IPDatabase error: {}", e.getMessage());
            return null;
        }
    }
}
