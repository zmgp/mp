package com.shu.ming.mp.modules.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author JGod
 * @create 2021-10-16-16:03
 */
@Data
public class LoginDTO {

    /**
     * 用户名称
     */
    @NotBlank(message = "请填写用户名")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "请填写密码")
    private String password;
}
