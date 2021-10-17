package com.shu.ming.mp.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author JGod
 * @create 2021-10-16-16:21
 */
@Component
@Slf4j
public class JWTUtils {

    public static String secret;
    public static long expire;

    /**
     * 解析token，获取权限
     * @param token
     * @return
     */
    public static List<Integer> resolveTokenPermission(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        return (List<Integer>)jwt.getPayload("authority");
    }

    @Value("${jwtSecret}")
    public void setSecret(String secret) {
        log.info("设置jwt私密为: {} " , secret);
        JWTUtils.secret = secret;
    }

    @Value("${expire}")
    public void setExpire(long expire) {
        log.info("设置过期时间为: {} 小时",  expire);
        JWTUtils.expire = expire;
    }

    /**
     * 生成Token
     * @param id 用户id
     * @param userName 用户名
     * @param authority 用户权限
     * @return
     */
    public static String createToken(long id, String userName, List<Integer> authority) {
        byte[] key = secret.getBytes();
        LocalDateTime now = LocalDateTime.now().plusHours(expire);
        return JWT.create()
                .setPayload("id", id)
                .setPayload("userName", userName)
                .setPayload("authority", authority)
                .setPayload("expire", now)
                .setKey(key)
                .sign();
    }


    /**
     * 对token进行解析
     * @param token
     */
    public static boolean resolveToken(String token) {
        byte[] key = secret.getBytes();
        boolean validate = JWT.of(token).setKey(key).validate(0);
        Preconditions.checkArgument(validate, "token 失效");

        return validate;
    }


    /**
     * 对token进行验证
     * @param token
     * @return
     */
    public static void validToken(String token){
        JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256(secret.getBytes()));
    }


}
