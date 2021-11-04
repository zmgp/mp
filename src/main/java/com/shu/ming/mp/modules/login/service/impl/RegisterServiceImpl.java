package com.shu.ming.mp.modules.login.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.commons.util.BloomFilterUtil;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.commons.util.IdentifyCode;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.modules.login.bean.Demo;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.Convert;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import com.shu.ming.mp.modules.login.mapper.DemoMapper;
import com.shu.ming.mp.modules.login.mapper.LoginMapper;
import com.shu.ming.mp.modules.login.mapper.RegisterMapper;
import com.shu.ming.mp.modules.login.service.LoginService;
import com.shu.ming.mp.modules.login.service.RegisterService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, UserInfo> implements RegisterService {

    private RegisterMapper registerMapper;
    private RedisUtil redisUtil;
    BloomFilterUtil bloomFilterUtil;
    /**
     * 使用构造器的方式注入
     * @param registerMapper
     */
    @SuppressWarnings("all")
    public RegisterServiceImpl(RegisterMapper registerMapper, RedisUtil redisUtil, BloomFilterUtil bloomFilterUtil){
        this.registerMapper = registerMapper;
        this.redisUtil = redisUtil;
        this.bloomFilterUtil = bloomFilterUtil;
    }

    /*
     *根据用户名查询注册名，注册名相同返回false
     */
    @Override
    public UserInfo findUserByName(String username) {
        UserInfo user = registerMapper.findUserByName(username);
        return user;
    }


    @Override
    public boolean existEmailAddress(String email) {
        return bloomFilterUtil.containsEmail(email);
    }

    private final String VERIFICATION_CODE_PREFIX = "verification:";

    @Async
    @Override
    public void sendRegisterEmail(String email) {
        String verificationCode = IdentifyCode.code();
        redisUtil.setEx(VERIFICATION_CODE_PREFIX.concat(email), verificationCode, 15, TimeUnit.MINUTES);
        EmailUtil.sendVerificationCode(email, verificationCode);
    }

    @Override
    public boolean userExists(String userName, String eamil) {
        return countByNameAndEmail(userName, eamil) != 0;
    }

    @Override
    public boolean judgeVerifyCode(String email, String code) {
        String key = redisUtil.get(VERIFICATION_CODE_PREFIX.concat(email));
        if (key != null && key.equals(code)){
            redisUtil.expire(VERIFICATION_CODE_PREFIX.concat(email), 0, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    private int countByNameAndEmail(String userName, String eamil){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("username", userName)
                .or()
                .eq("email", eamil);
        return registerMapper.selectCount(queryWrapper);
    }

    @Override
    public void insertOneUser(RegisterDTO registerDTO) {
        UserInfo userInfo = Convert.convertToRegisterDTO(registerDTO);
        registerMapper.insert(userInfo);
        bloomFilterUtil.addKey(registerDTO);
    }
}
