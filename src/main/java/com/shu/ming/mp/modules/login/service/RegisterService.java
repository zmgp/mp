package com.shu.ming.mp.modules.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegisterService extends IService<UserInfo> {

    UserInfo findUserByName(String username);

    void insertOneUser(RegisterDTO registerDTO);

    boolean existEmailAddress(String email);


    /**
     * 发送注册验证码
     * @param email  邮箱
     */
    void sendRegisterEmail(String email);

    /**
     *
     * @return
     * @param userName 用户名
     * @param password 密码
     */
    boolean userExists(String userName, String password);

    /**
     * 验证邮箱验证码是否正确
     * @param code
     * @return
     */
    boolean judgeVerifyCode(String email, String code);
}
