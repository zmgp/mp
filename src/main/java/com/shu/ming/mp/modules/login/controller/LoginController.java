package com.shu.ming.mp.modules.login.controller;

import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.enums.ResultCode;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.service.AuthorityService;
import com.shu.ming.mp.modules.login.service.LoginService;
import com.shu.ming.mp.util.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public Result login (@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        // 判断当前用户是否存在
        UserInfo user = loginService.findOneByNameAndPwd(loginDTO.getUsername(), loginDTO.getPassword());
        if (user == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
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
        // JedisUtils.setObject(user.getUsername(), null, (int) JWTUtils.expire * 60 * 60);
        return Result.success();
    }
}
