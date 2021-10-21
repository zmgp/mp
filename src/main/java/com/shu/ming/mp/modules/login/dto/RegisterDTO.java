package com.shu.ming.mp.modules.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class RegisterDTO {
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

    /*
     *
     */
    @NotBlank(message = "请填写邮箱")
    private String email;
}
