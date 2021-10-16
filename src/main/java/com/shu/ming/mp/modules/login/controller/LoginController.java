package com.shu.ming.mp.modules.login.controller;

import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.domain.ResultCode;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("登录功能")
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login (@RequestBody LoginDTO loginDTO) {
        boolean isEExist = loginService.isExist(loginDTO);
        if (!isEExist){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }

        return Result.success();
    }
}
