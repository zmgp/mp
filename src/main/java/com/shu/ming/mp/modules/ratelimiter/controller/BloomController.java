package com.shu.ming.mp.modules.ratelimiter.controller;

import com.shu.ming.mp.annotation.RequestLimit;
import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.util.BloomFilterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JGod
 * @create 2021-10-17-17:34
 */
@RestController
@Slf4j
@Api("布隆过滤器测试demo")
@RequestMapping("/bloom")
@AllArgsConstructor
public class BloomController {

    BloomFilterUtil bloomFilterUtil;

    @RequestLimit
    @GetMapping("/t1")
    @ApiOperation("布隆测试 set")
    public Result setEmailKey(String key) {
        bloomFilterUtil.addEmailKey(key);
        return Result.success();
    }

    @RequestLimit
    @GetMapping("/t2")
    @ApiOperation("布隆测试 exists")
    public Result existsEmailKey(String key) {
        boolean contains = bloomFilterUtil.containsEmail(key);
        return Result.success(contains ? "存在" : "不存在");
    }

    @RequestLimit
    @GetMapping("/t3")
    @ApiOperation("布隆测试 set")
    public Result setNameKey(String key) {
        bloomFilterUtil.addNameKey(key);
        return Result.success();
    }

    @RequestLimit
    @GetMapping("/t4")
    @ApiOperation("布隆测试 exists")
    public Result existsNameKey(String key) {
        boolean contains = bloomFilterUtil.containsName(key);
        return Result.success(contains ? "存在" : "不存在");
    }
}
