package com.shu.ming.mp.modules.login.dto;

import lombok.Data;

/**
 * @author JGod
 * @create 2021-10-16-16:03
 */
@Data
public class LoginDTO {

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
