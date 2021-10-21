package com.shu.ming.mp.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @author JGod
 * @create 2021-10-19-19:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 指定second 时间内，API最多的请求次数
     * 次数： 不间断点击大约 4s 点击 20 次， 故而设置 15
     */
    int count() default 15;

    /**
     * 指定时间second，redis数据过期时间
     */
    int second() default 4;

    /**
     * 指定超过限制后，多久后能重新访问
     */
    int expire() default 60;

}
