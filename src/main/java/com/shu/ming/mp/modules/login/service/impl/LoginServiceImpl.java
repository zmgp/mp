package com.shu.ming.mp.modules.login.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.mapper.DemoMapper;
import com.shu.ming.mp.modules.login.mapper.LoginMapper;
import com.shu.ming.mp.modules.login.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UserInfo> implements LoginService {

    private LoginMapper loginMapper;

    /**
     * 使用构造器的方式注入
     * @param loginMapper
     */
    public LoginServiceImpl(LoginMapper loginMapper){
        this.loginMapper = loginMapper;
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

}
