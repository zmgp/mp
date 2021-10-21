package com.shu.ming.mp.handler;

import com.google.common.util.concurrent.RateLimiter;
import com.shu.ming.mp.exception.BusyException;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author JGod
 * @create 2021-10-22-22:00
 */
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    /**
     * 通过构造函数初始化限速器
     */
    public RateLimiterInterceptor(RateLimiter rateLimiter) {
        super();
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(this.rateLimiter.tryAcquire()) {
            /**
             * 成功获取到令牌
             */
            return true;
        }

        /**
         * 获取失败，直接响应“错误信息”
         * 也可以通过抛出异常，通过全全局异常处理器响应客户端
         */
        throw new BusyException("服务器繁忙");
    }
}
