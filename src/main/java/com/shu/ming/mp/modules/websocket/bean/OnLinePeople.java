package com.shu.ming.mp.modules.websocket.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author JGod
 * @create 2021-10-16-16:49
 */
@Data
public class OnLinePeople {

    /**
     * 用户名
     */
    private String name;


    /**
     * 登录 IP
     */
    private String ip;


    /**
     * 上线时间
     */
    private LocalDateTime upTime;

    /**
     * 下线时间
     */
    private LocalDateTime downTime;

    /**
     * 请求次数
     */
    private volatile int requestCount = 0;

    /**
     * 登录浏览器
     */
    private String browser;

    /**
     * 登录地址
     */
    private String addr;


}
