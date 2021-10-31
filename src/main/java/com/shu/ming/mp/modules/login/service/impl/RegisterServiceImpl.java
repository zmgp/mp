package com.shu.ming.mp.modules.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, UserInfo> implements RegisterService {
    private RegisterMapper registerMapper;

    /**
     * 使用构造器的方式注入
     * @param registerMapper
     */
    public RegisterServiceImpl(RegisterMapper registerMapper){
        this.registerMapper = registerMapper;
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
    public void insertOneUser(RegisterDTO registerDTO) {
        UserInfo userInfo = Convert.convertToRegisterDTO(registerDTO);
        registerMapper.insertOneUser(userInfo);
    }

    @Override
    public boolean existEmailAddress(String email) {
        UserInfo userInfo = registerMapper.existEmailAddress(email);
        if (userInfo == null){
            return true;
        }
        return false;

    }
}
