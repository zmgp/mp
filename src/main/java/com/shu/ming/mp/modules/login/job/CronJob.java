package com.shu.ming.mp.modules.login.job;

import com.shu.ming.mp.modules.websocket.service.WebService;
import com.shu.ming.mp.commons.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author JGod
 * @create 2021-10-19-19:29
 */
@Slf4j
@Component
public class CronJob {

    private RedisUtil redisUtil;
    private WebService webService;
    private final String PREFIX = "cron:onLine:";

    public CronJob(RedisUtil redisUtil, WebService webService){
        this.redisUtil = redisUtil;
        this.webService = webService;
    }


    /**
     * 统计在线人数
     * 每十分钟统计一次
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void onLineCount(){
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        int size = webService.size();
        redisUtil.lRightPush(PREFIX.concat(date), size + "");
        log.info("当前在线人数： {}", size);
    }

}
