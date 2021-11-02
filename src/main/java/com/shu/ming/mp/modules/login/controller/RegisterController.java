package com.shu.ming.mp.modules.login.controller;


import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.commons.enums.ResultCode;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.commons.util.IdentifyCode;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@Slf4j
@Api("注册功能")
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private RegisterService registerService;

    /*
     *简单注册流程：
     *1.用户输入密码和用户名及邮箱
     *2.点击提交如果查询到用户名 返回失败 没查询到 跳转到发送邮箱界面 即该页面上跳转一个页面
     *3.输入邮箱验证后输入确定，将用户的用户名、密码、邮箱写入数据库 完成注册，5s后再返回到登录页面
     */
    @ApiOperation("用户信息注册")
    @PostMapping("/register")
    @PassToken
    public Result registerpage1 ( @RequestBody RegisterDTO registerDTO,HttpServletRequest request) {
        // 判断当前用户是否存在
        UserInfo user = registerService.findUserByName(registerDTO.getUsername());
        if (user != null){  //已存在用户 跳转判断邮箱是否重复 邮箱
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }

        if(! registerService.existEmailAddress(registerDTO.getEmail())){
            return Result.failure(ResultCode.DATA_ALREADY_EXISTED);
        }
//        //根据用户邮箱发送邮件
//        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
//        email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，qq邮箱为smtp.qq.com
//        email.setCharset("utf-8");//设置发送的字符类型
//        try {
//            email.addTo(registerDTO.getEmail());
//            email.setFrom("1017459962@qq.com","ming");
//            email.setAuthentication("1017459962@qq.com","ming");//设置发送人的邮箱和授权码
//            email.setSubject("mp平台用户注册码");//设置发送主题
//            email.setMsg("发送内容");//设置发送内容
//            email.send();//进行发送
//        } catch (EmailException e) {
//            log.info("邮箱发送失败");
//            e.printStackTrace();
//        }
        String code = IdentifyCode.code();
        HttpSession session = request.getSession(false);
        if(session == null){
            //创建session，并拿到JSESSIONID
            session = request.getSession();
        }
        //设置session有效时间，默认是1800s
        session.setMaxInactiveInterval(1200);
        //将验证码放入session中
        session.setAttribute("code",code);
        //将用户邮箱放入session中 防止用户篡改
        session.setAttribute("email",registerDTO.getEmail());
        EmailUtil.sendEmail(registerDTO.getEmail(),"邮箱注册码",code);
        log.info("邮箱发送");
        return Result.success();
    }

    @ApiOperation("email确认成功后注册成功 用户信息写入数据库")
    @PostMapping("/emailReg")
    @PassToken
    public Result registerPage2(@RequestBody RegisterDTO registerDTO, @RequestParam String mycode, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            session = request.getSession();
        }
        //判断用户有没有瞎改用户名，瞎改了查数据库是否存在 存在返回错误，不存在默许
        UserInfo user = registerService.findUserByName(registerDTO.getUsername());
        if (user != null){  //已存在用户 跳转判断邮箱是否重复 邮箱
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
        String code1 = session.getAttribute("code").toString();
        String email1 = session.getAttribute("email").toString();
        if(registerDTO.getEmail().equals(email1)){
            if(mycode.equals(code1)){
                registerService.insertOneUser(registerDTO);
                return Result.success();
            }
        }

        return Result.failure(ResultCode.DATA_IS_WRONG);
    }

//经过讨论 不需要
//    @ApiOperation("email重发")
//    @PostMapping("/emailResend")
//    public Result emailResend(@RequestBody RegisterDTO registerDTO, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        String code = IdentifyCode.code();
//        //设置session有效时间，默认是1800s
//        session.setMaxInactiveInterval(1200);
//        //将验证码放入session中
//        session.setAttribute("code",code);
//        EmailUtil.sendEmail(registerDTO.getEmail(),"邮箱注册码",code);
//        return Result.success();
//    }

}
