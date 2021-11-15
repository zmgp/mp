package com.shu.ming.mp.modules.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;



@Data
public class PWDverifyDTO {
    /**
     * 用户邮箱
     */
    @NotBlank(message = "请填写邮箱")
    private String email;


    /**
     * 密码
     */
    @NotBlank(message = "请填写密码")
    private String password;



    @NotBlank(message = "请填写邮箱验证码")
    private String code;
}
