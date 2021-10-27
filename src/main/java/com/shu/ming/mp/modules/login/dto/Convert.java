package com.shu.ming.mp.modules.login.dto;

import com.shu.ming.mp.modules.login.bean.UserInfo;

public class Convert {
    public static UserInfo convertToRegisterDTO(RegisterDTO registerDTO){
        if (registerDTO == null){
            throw new RuntimeException("registerDTO 不能为 null");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerDTO.getUsername());
        userInfo.setPassword(registerDTO.getPassword());
        userInfo.setEmail(registerDTO.getEmail());

        return userInfo;
    }
}
