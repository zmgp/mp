package com.shu.ming.mp.modules.login.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.mapper.DemoMapper;
import com.shu.ming.mp.modules.login.mapper.LoginMapper;
import com.shu.ming.mp.modules.login.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UserInfo> implements LoginService {

    private LoginMapper loginMapper;
    private RedisUtil redisUtil;


    /**
     * 使用构造器的方式注入
     * @param loginMapper
     */
    @SuppressWarnings("all")
    public LoginServiceImpl(LoginMapper loginMapper,RedisUtil redisUtil){
        this.loginMapper = loginMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean isExist(LoginDTO loginDTO) {
        // todo 业务逻辑
        List<UserInfo> exist = loginMapper.isExist(loginDTO);
        return exist.size() == 1;
    }

    @Override
    public UserInfo findUserById(int id) {
        return loginMapper.findUserById(id);
    }

    /**
     * 根据用户名和密码查找一个用户
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserInfo findOneByNameAndPwd(String userName, String password){
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper
                .eq("username", userName)
                .eq("password", password);
        return this.getOne(userInfoQueryWrapper);
    }

    private final String VERIFICATION_CODE_PREFIX = "verification:";
    /*
     * 设置用户的jtw和过期时间
     */
    @Override
    public void setUserJTW(String username, String token) {

        redisUtil.setEx(username,token,3, TimeUnit.DAYS);
    }
    /*
     * 设置得到的uuid 和他请求的图形验证码到redis缓存中
     */
    @Override
    public void setUseritfCode(String uuid, String itfCode) {
        redisUtil.setEx(VERIFICATION_CODE_PREFIX.concat(uuid),itfCode,5,TimeUnit.MINUTES);
    }

    /*
     * 根据uuid拿到验证码和前端比较是否一致
     */
    @Override
    public boolean judgeVerifyCode(String uuid, String itfCode) {
        String key = redisUtil.get(VERIFICATION_CODE_PREFIX.concat(uuid));
        if (key != null && key.equals(itfCode)){
            //redisUtil.expire(VERIFICATION_CODE_PREFIX.concat(itfCode), 0, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }
}
