package com.shu.ming.mp.modules.login.controller;


import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.commons.enums.ResultCode;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.dto.PWDverifyDTO;
import com.shu.ming.mp.modules.login.service.ForgetPWDService;
import com.shu.ming.mp.modules.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Api("忘记密码功能")
@RequestMapping("/forget")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ForgetPWDController {

    private ForgetPWDService forgetPWDService;


    @ApiOperation("发送验证码")
    @PassToken
    @PostMapping("/sendEmail")
    public Result sendEmail(@RequestBody PWDverifyDTO pwdVerifyDTO){
        log.info("发送密码邮箱为"+pwdVerifyDTO.getEmail());
        UserInfo userInfo = forgetPWDService.forgetUser(pwdVerifyDTO.getEmail());
        if(userInfo == null){
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        log.info(userInfo.getUsername());
        forgetPWDService.sendForgetEmail(pwdVerifyDTO.getEmail());
        return Result.success();
    }

    @ApiOperation("验证验证码")
    @PassToken
    @PostMapping("/verifyEmail")
    public Result verifyEmail(@RequestBody PWDverifyDTO pwdVerifyDTO){
        log.info("得到得邮箱为"+pwdVerifyDTO.getEmail());
        boolean flag = forgetPWDService.judgeVerifyCode(pwdVerifyDTO.getEmail(), pwdVerifyDTO.getCode());

        if(!flag){
            return Result.failure(ResultCode.VERIFICATION_CODE_ERROR);
        }

        UserInfo userInfo = forgetPWDService.forgetUser(pwdVerifyDTO.getEmail());
        String username = userInfo.getUsername();
        Map<String, String> user = new HashMap<>();
        user.put("username",username);
        return Result.success(user);
    }

    @ApiOperation("重新设置密码")
    @PassToken
    @PostMapping("/resetPWD")
    public Result resetPWD(@RequestBody PWDverifyDTO pwdVerifyDTO){
        log.info("得到得邮箱为"+pwdVerifyDTO.getEmail());
        UserInfo userInfo = forgetPWDService.forgetUser(pwdVerifyDTO.getEmail());
        final boolean flag = forgetPWDService.judgeVerifyCode2(pwdVerifyDTO.getEmail(), pwdVerifyDTO.getCode());
        if(!flag){
            log.info("-1");
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        forgetPWDService.updateOneUserPWD(pwdVerifyDTO.getPassword(),userInfo.getUsername());
        return Result.success();
    }
}
