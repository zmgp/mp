package com.shu.ming.mp.modules.ratelimiter.aop;

import com.shu.ming.mp.commons.annotation.RequestLimit;
import com.shu.ming.mp.commons.exception.AccessLimitException;
import com.shu.ming.mp.commons.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author JGod
 * @create 2021-10-19-19:51
 */
@Slf4j
@Component
@Aspect
@AllArgsConstructor
public class RequestLimitAOP {

    RedisUtil redisUtil;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestCut(RequestLimit limit) {
    }

    @Before(value = "requestCut(limit)", argNames = "joinPoint,limit")
    public void requestLimit(JoinPoint joinPoint, RequestLimit limit) throws AccessLimitException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = request.getRemoteAddr();
        log.info("访问者的IP地址为： {}", ip);

        String url = request.getRequestURL().toString();
        log.info("访问者访问的url为： {}", url);

        String key = "req_limit_".concat(url).concat("_").concat(ip);
        String key2 = key.concat("helper");
        String key3 = key.concat("helper_2");
        boolean checkResult = checkByRedis(limit, key, key2, key3);

        if (!checkResult){
            log.info("requestLimited," + "[用户ip:{}],[访问地址:{}]超过了限定的次数[{}]次", ip, url, limit.count());
            throw new AccessLimitException("请求过于频繁");
        }
    }

    /**
     * 判断是否超出限制
     * @param limit
     * @param key
     * @return
     */
    private boolean checkByRedis(RequestLimit limit, String key, String key2, String key3) {
        // 是否包含key3
        boolean k3 = redisUtil.hasKey(key3);
        if (!redisUtil.hasKey(key2) && !k3){
            redisUtil.set(key, "0");
            redisUtil.setEx(key2, "", limit.second(), TimeUnit.SECONDS);
        }
        long requestCount = redisUtil.incrBy(key, 1L);
        redisUtil.setEx(key, requestCount + "", limit.expire(), TimeUnit.SECONDS);
        log.info("当前请求次数为： {}", requestCount);
        if (requestCount > limit.count()){
            if (!k3) {
                redisUtil.setEx(key3, "", limit.expire(), TimeUnit.SECONDS);
            }
            return false;
        }
        return true;
    }
}
