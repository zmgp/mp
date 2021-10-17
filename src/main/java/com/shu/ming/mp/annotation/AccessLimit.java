package com.shu.ming.mp.annotation;

/**
 * @author JGod
 * @create 2021-10-19-19:55
 */
public @interface AccessLimit {
    /**
     * 指定second 时间内，API最多的请求次数
     */
    int times() default 3;

    /**
     * 指定时间second，redis数据过期时间
     */
    int second() default 10;
}
