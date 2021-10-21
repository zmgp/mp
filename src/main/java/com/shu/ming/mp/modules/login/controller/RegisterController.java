package com.shu.ming.mp.modules.login.controller;


import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.enums.ResultCode;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import com.shu.ming.mp.modules.login.service.AuthorityService;
import com.shu.ming.mp.modules.login.service.LoginService;
import com.shu.ming.mp.modules.login.service.RegisterService;
import com.shu.ming.mp.util.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
@Api("注册功能")
@RequestMapping("/register")
@AllArgsConstructor
@NoArgsConstructor

/*
 *简单注册流程：
 *1.用户输入密码和用户名及邮箱
 *2.点击提交如果查询到用户名 返回失败 没查询到 跳转到发送邮箱界面
 *3.输入邮箱验证后输入确定，将用户的用户名、密码、邮箱写入数据库 完成注册，5s后再返回到登录页面
 */
public class RegisterController {

    private RegisterService registerService;

    @ApiOperation("用户信息注册")
    @PostMapping("/register")
    public Result loginPage1 ( @RequestBody RegisterDTO registerDTO) {
        // 判断当前用户是否存在
        UserInfo user = registerService.findUserByName(registerDTO.getUsername());
        if (user != null){  //已存在用户 跳转发邮箱
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
        //根据用户邮箱发送邮件
        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，qq邮箱为smtp.qq.com
        email.setCharset("utf-8");//设置发送的字符类型
        try {
            email.addTo(registerDTO.getEmail());
            email.setFrom("1017459962@qq.com","ming");
            email.setAuthentication("1017459962@qq.com","ming");//设置发送人的邮箱和授权码
            email.setSubject("mp平台用户注册码");//设置发送主题
            email.setMsg("发送内容");//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            log.info("邮箱发送失败");
            e.printStackTrace();
        }
        return Result.success();
    }


}
