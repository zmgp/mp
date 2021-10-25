package com.shu.ming.mp.modules.websocket.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.commons.util.IPUtil;
import com.shu.ming.mp.modules.websocket.service.WebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author JGod
 * @create 2021-10-14-14:04
 */
@RestController
@Slf4j
@Api("WebSocketDemo")
@RequestMapping("/websocket")
@AllArgsConstructor
public class WebSocketController {

    private WebService webService;
    DatabaseReader databaseReader;

    @GetMapping("/get/send")
    @ApiOperation("发送get")
    public Result sendGet(@RequestParam("token") String token ,
                          @RequestParam("message") String message) {
        webService.sendMessage(token , message);
        return Result.success();
    }

    @PostMapping("/post/send")
    public Result sendPost(@RequestParam("token") String token , @RequestBody String body) {
        webService.sendMessage(token , body);
        return Result.success();
    }

    @PostMapping("/broadcast")
    @ApiOperation("广播")
    public Result broadcast(@RequestBody String body) {
        webService.broadcast(body);
        return Result.success();
    }

    @GetMapping("/clients")
    @ApiOperation("目前在线")
    public Result getClientsNum(){
        return Result.success(webService.size());
    }

    @GetMapping("/onLine")
    @ApiOperation("获得当前在线信息")
    public Result getOnLinePeople(){
        return Result.success(webService.onLinePeople());
    }

    @PassToken
    @GetMapping("/ip")
    @ApiOperation("进行ip映射")
    public Result ip(@RequestParam("message") String ip) throws Exception {
        return Result.success(IPUtil.getAddress(databaseReader, ip));
    }
}
