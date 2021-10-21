package com.shu.ming.mp.modules.ratelimiter.controller;

import com.shu.ming.mp.annotation.RequestLimit;
import com.shu.ming.mp.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JGod
 * @create 2021-10-20-20:31
 */
@RestController
@Slf4j
@Api("限制测试demo")
@RequestMapping("/limit")
@AllArgsConstructor
public class LimitController {

    @RequestLimit
    @GetMapping("/t1")
    @ApiOperation("限制测试1")
    public Result t1() {
        return Result.success();
    }

    @GetMapping("/t2")
    @ApiOperation("限制测试2")
    public Result t2() {
        return Result.success();
    }
}
