package com.shu.ming.mp.modules.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.commons.util.BloomFilterUtil;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.commons.util.IdentifyCode;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.mapper.ForgetPWDMapper;
import com.shu.ming.mp.modules.login.service.ForgetPWDService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class ForgetPWDServiceImpl extends ServiceImpl<ForgetPWDMapper, UserInfo> implements ForgetPWDService {

    private ForgetPWDMapper forgetPWDMapper;
    private RedisUtil redisUtil;

    @SuppressWarnings("all")
    public ForgetPWDServiceImpl(ForgetPWDMapper forgetPWDMapper, RedisUtil redisUtil,BloomFilterUtil bloomFilterUtil ) {
        this.forgetPWDMapper = forgetPWDMapper;
        this.redisUtil = redisUtil;
    }


    /**
     * 根据邮箱查找一个用户
     * @param email
     * @return
     */
    @Override
    public UserInfo forgetUser(String email) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("email", email);
        return this.getOne(userInfoQueryWrapper);
    }


    private final String VERIFICATION_CODE_PREFIX = "verificationPWD :";
    @Async
    @Override
    public void sendForgetEmail(String email) {
        String verificationCode = IdentifyCode.code();
        redisUtil.setEx(VERIFICATION_CODE_PREFIX.concat(email), verificationCode, 15, TimeUnit.MINUTES);
        EmailUtil.sendVerificationCode(email, verificationCode);
    }

    @Override
    public boolean judgeVerifyCode(String email, String code) {
        String key = redisUtil.get(VERIFICATION_CODE_PREFIX.concat(email));
        if (key != null && key.equals(code)){
            //redisUtil.expire(VERIFICATION_CODE_PREFIX.concat(email), 5, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    @Override
    public boolean judgeVerifyCode2(String email, String code) {
        String key = redisUtil.get(VERIFICATION_CODE_PREFIX.concat(email));
        if (key != null && key.equals(code)){
            redisUtil.expire(VERIFICATION_CODE_PREFIX.concat(email), 0, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    @Override
    public void updateOneUserPWD(String password, String username) {
        forgetPWDMapper.updateOneUserPWD(password,username);
    }
}
