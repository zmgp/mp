package com.shu.ming.mp.modules.login.controller;


import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.util.RandomUtil;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.commons.enums.ResultCode;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.commons.util.IdentifyCode;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import com.shu.ming.mp.modules.login.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
@Api("注册功能")
@RequestMapping("/register")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterController {
    private RedisUtil redisUtil;
    private RegisterService registerService;

    /*
     *简单注册流程：
     *1.用户输入密码和用户名及邮箱
     *2.点击提交如果查询到用户名 返回失败 没查询到 跳转到发送邮箱界面 即该页面上跳转一个页面
     *3.输入邮箱验证后输入确定，将用户的用户名、密码、邮箱写入数据库 完成注册，5s后再返回到登录页面
     */

    @PassToken
    @ApiOperation("email发送")
    @PostMapping("/emailSend")
    public Result sendEmail( String email){
        log.info("发送邮箱为"+email);
        if (registerService.existEmailAddress(email)){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
        registerService.sendRegisterEmail(email);
        return Result.success();
    }


    @PassToken
    @ApiOperation("用户注册")
    @PostMapping("/userRegister")
    public Result register(@RequestBody RegisterDTO registerDTO){
        boolean flag = registerService.judgeVerifyCode(registerDTO.getEmail(), registerDTO.getCode());
        if (!flag){
            return Result.failure(ResultCode.VERIFICATION_CODE_ERROR);
        }
        flag = registerService.userExists(registerDTO.getUsername(), registerDTO.getEmail());
        if (flag){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
        registerService.insertOneUser(registerDTO);
        return Result.success();
    }
}
