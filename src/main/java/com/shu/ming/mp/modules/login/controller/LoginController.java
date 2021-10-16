package com.shu.ming.mp.modules.login.controller;

import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.domain.ResultCode;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api("登录测试")
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    @ApiOperation("判断登录用户信息，存在即返回")
    @PostMapping("/userlogin")
    public Result isExist (@RequestBody UserInfo userInfo){
        List<UserInfo> userList = loginService.isExist(userInfo);
        if(userList != null){
            return Result.success(ResultCode.SUCCESS);
        }else{
            return Result.failure(ResultCode.USER_LOGIN_ERROR,0);
        }
    }
}
