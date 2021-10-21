package com.shu.ming.mp.modules.login.bean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@TableName("user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    /**
     * 用户Id
     */
    private int id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /*
     *用户邮箱
     */
    private String email;
}
