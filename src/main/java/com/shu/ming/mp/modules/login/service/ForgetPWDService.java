package com.shu.ming.mp.modules.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import org.apache.ibatis.annotations.Param;

public interface ForgetPWDService extends IService<UserInfo> {

    UserInfo forgetUser(@Param("email") String email);

    /**
     * 发送忘记密码验证码
     * @param email  邮箱
     */
    void sendForgetEmail(String email);

    boolean judgeVerifyCode(String email, String code);

    boolean judgeVerifyCode2(String email, String code);

    void updateOneUserPWD(String password,String username);
}
