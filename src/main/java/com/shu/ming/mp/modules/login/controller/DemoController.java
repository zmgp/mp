package com.shu.ming.mp.modules.login.controller;

import cn.hutool.http.HttpResponse;
import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.modules.login.bean.Demo;
import com.shu.ming.mp.modules.login.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JGod
 * @create 2021-10-18-18:51
 */
@RestController
@Slf4j
@Api("demo示例")
@RequestMapping("/login1")
public class DemoController {

    private DemoService demoService;
    private RedisTemplate redisTemplate;
    public DemoController(DemoService demoService, RedisTemplate redisTemplate){
        this.demoService = demoService;
        this.redisTemplate = redisTemplate;
    }

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
}
