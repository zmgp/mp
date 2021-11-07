package com.shu.ming.mp.modules.login.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.commons.enums.ResultCode;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.service.AuthorityService;
import com.shu.ming.mp.modules.login.service.LoginService;
import com.shu.ming.mp.commons.util.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
@Api("登录功能")
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    private LoginService loginService;

    private AuthorityService authorityService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    @PassToken
    public Result login (@Validated @RequestBody LoginDTO loginDTO, @RequestParam String uuid, HttpServletRequest request, HttpServletResponse response) {
        //判断验证码是否正确
        boolean flag = loginService.judgeVerifyCode(uuid,loginDTO.getIdfCode());
        if(!flag){
            return Result.failure(ResultCode.VERIFICATION_CODE_ERROR);
        }
        // 判断当前用户是否存在
        UserInfo user = loginService.findOneByNameAndPwd(loginDTO.getUsername(), loginDTO.getPassword());
        if (user == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }

//      HttpSession session = request.getSession();
//      String captchaCode = session.getAttribute("captchaCode").toString();
//        if(!myCaptcha.equals(captchaCode)){
//            return Result.failure(ResultCode.DATA_IS_WRONG);
//        }
        // 获得该用户的权限
        List<Integer> authority = authorityService.getAuthorityByUserId(user.getId());
        // 颁发token
        String token = JWTUtils.createToken(user.getId(), user.getUsername(), authority);

        // 添加cookie
        Cookie cookie = new Cookie("token", token);
        // 设置过期时长
        cookie.setMaxAge((int) JWTUtils.expire * 60 * 60);
        // 添加cookie
        response.addCookie(cookie);
        // 加入redis
        loginService.setUserJTW(user.getUsername(), token);
        //RedisUtil.
        return Result.success();
    }

    @ApiOperation("用户验证码生成")
    @GetMapping("/captcha")
    @PassToken
    public void formCaptcha (String uuid,HttpServletRequest request, HttpServletResponse response) {
                    log.info("啊啊啊");
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            //定义图形验证码的长、宽、验证码字符数、干扰元素个数
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
            //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
            //图形验证码写出，可以写出到文件，也可以写出到流
            //captcha.write("d:/circle.png");
        try {
            captcha.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("获取验证码失败>>>>", e);
           // return Result.failure("获取验证码失败",70002,null);
        }
        String captchaCode = captcha.getCode();
        loginService.setUseritfCode(uuid,captchaCode);
        //HttpSession session = request.getSession();
        //session.setAttribute("captchaCode",captchaCode);
        //session.setMaxInactiveInterval(1200);
       // return Result.success();
    }


}
