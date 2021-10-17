package com.shu.ming.mp.modules.login.controller;

import com.shu.ming.mp.annotation.PassToken;
import com.shu.ming.mp.annotation.UserLoginToken;
import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.enums.ResultCode;
import com.shu.ming.mp.modules.login.bean.Demo;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.service.AuthorityService;
import com.shu.ming.mp.modules.login.service.DemoService;
import com.shu.ming.mp.modules.login.service.LoginService;
import com.shu.ming.mp.util.JWTUtils;
import com.shu.ming.mp.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author JGod
 * @create 2021-10-18-18:51
 */
@RestController
@Slf4j
@Api("demo示例")
@RequestMapping("/login1")
@AllArgsConstructor
public class DemoController {

    private LoginService loginService;
    private AuthorityService authorityService;
    private DemoService demoService;
    private RedisTemplate redisTemplate;
    private RedisUtil redisUtil;


    @ApiOperation("获得全部的用户信息")
    @GetMapping("/admin")
    public Result getList() {
        List<Demo> demo = demoService.list();
        return Result.success(demo);
    }

    /**
     * // todo 重要
     * @param id
     * @return
     */
    @ApiOperation("以不同的方式获取用户的全部信息")
    @GetMapping("/self")
    @ApiImplicitParam(name = "id", value = "用户ID", defaultValue = "1", required = true)
    public Result self(int id) {
        List<Demo> demo = demoService.self(id);
        try {
            if (demo != null) {
                redisTemplate.opsForValue().set(id, demo.toString());
            }
        }catch (Exception e){
            log.info("redis未连接");
        }
        return Result.success(demo);
    }


    @GetMapping("/redis")
    @ApiOperation("redis操作示例")
    @ApiImplicitParam(name = "key", value = "redis的Key", defaultValue = "1", required = true)
    public Result getRedis(String key){
        Object o = redisTemplate.opsForValue().get(key);
        return Result.success(o);
    }

    @GetMapping("/jwt")
    @ApiOperation("生成token")
    public Result jwt(){

        return Result.success(JWTUtils.createToken(1, "hue", new ArrayList<>()));
    }

    @PassToken
    @GetMapping("/auth")
    @ApiOperation("权限测试1")
    public Result auth(){
        return Result.success();
    }


    @UserLoginToken
    @GetMapping("/auth1")
    @ApiOperation("权限测试2")
    public Result auth1(){
        return Result.success();
    }

    @UserLoginToken(permission = {})
    @GetMapping("/auth2")
    @ApiOperation("权限测试3")
    public Result auth2() {
        return Result.success();
    }

    @UserLoginToken(permission = {300})
    @GetMapping("/auth3")
    @ApiOperation("权限测试4")
    public Result auth3() {
        return Result.success();
    }

    @PassToken
    @GetMapping("/lt")
    @ApiOperation("登录测试")
    public Result login(String username, String password){
        // 判断当前用户是否存在
        UserInfo user = loginService.findOneByNameAndPwd(username, password);
        if (user == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        // 获得该用户的权限
        List<Integer> authority = authorityService.getAuthorityByUserId(user.getId());
        // 颁发token
        String token = JWTUtils.createToken(user.getId(), user.getUsername(), authority);

        // 加入redis
        redisUtil.setEx(username, token, 1, TimeUnit.MINUTES);
        return Result.success(token);
    }
}
