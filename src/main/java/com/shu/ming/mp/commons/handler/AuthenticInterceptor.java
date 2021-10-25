package com.shu.ming.mp.commons.handler;

import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.annotation.UserLoginToken;
import com.shu.ming.mp.commons.exception.NoLoginException;
import com.shu.ming.mp.commons.exception.NoPermisssionException;
import com.shu.ming.mp.commons.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Component
public class AuthenticInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            log.info("进行用户权限检验");
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new NoLoginException("请登录");
                }
                log.info("登录人携带的token为: {}", token);
                //对token进行验证
                JWTUtils.validToken(token);
                log.info("token经过了检验");
                //进行权限验证
                int[] permission = userLoginToken.permission();
                if (permission.length == 0){
                    return true;
                }
                List<Integer> permisssionList = JWTUtils.resolveTokenPermission(token);
                for (int p : permission) {
                    if (!permisssionList.contains(p)){
                        throw new NoPermisssionException("没有权限访问");
                    }
                }
                return true;
            }
        }
        return true;
    }
}
