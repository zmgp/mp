package com.shu.ming.mp.modules.websocket.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.annotation.UserLoginToken;
import com.shu.ming.mp.commons.exception.NoLoginException;
import com.shu.ming.mp.commons.exception.NoPermisssionException;
import com.shu.ming.mp.commons.util.IPUtil;
import com.shu.ming.mp.commons.util.JWTUtils;
import com.shu.ming.mp.modules.websocket.bean.OnLinePeople;
import com.shu.ming.mp.modules.websocket.service.WebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 *
 * 在线拦截器，用于统计用于操作
 *
 * @author JGod
 * @create 2021-10-17-17:25
 */
@Slf4j
public class OnLineInterceptor implements HandlerInterceptor {

    private WebService webService;
    private DatabaseReader databaseReader;

    public OnLineInterceptor(){
        webService = SpringUtil.getBean(WebService.WEBSOCKET_MANAGER_NAME);
        databaseReader = SpringUtil.getBean("databaseReader");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.onLine()) {
                return true;
            }
        }

        log.info("接收到一次请求... 在线统计拦截器执行...");
        //对token进行验证
        JWTUtils.validToken(token);
        log.info("token经过了检验");

        // 用户名
        String name = JWTUtils.resolveTokenName(token);

        Map<String, OnLinePeople> onLinePeople = webService.onLinePeopleMap();

        OnLinePeople people = onLinePeople.getOrDefault(name, new OnLinePeople());
        updatePeople(name, people, request);
        if (!onLinePeople.containsKey(name)){
            onLinePeople.put(name, people);
        }

        return true;
    }

    private void updatePeople(String name, OnLinePeople onLinePeople, HttpServletRequest request){
        onLinePeople.setName(name);
        onLinePeople.setIp(request.getRemoteAddr());
        onLinePeople.setBrowser(request.getHeader("USER-AGENT"));
        onLinePeople.setRequestCount(onLinePeople.getRequestCount() + 1);
        onLinePeople.setAddr(IPUtil.getAddress(databaseReader, onLinePeople.getIp()));
    }
}
