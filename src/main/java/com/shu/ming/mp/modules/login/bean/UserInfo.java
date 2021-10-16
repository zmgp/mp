package com.shu.ming.mp.modules.login.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("user_info")
@Data
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

}
