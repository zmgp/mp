package com.shu.ming.mp.modules.email.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.common.modules.email.service.EmailService;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.modules.email.service.IEmailService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author JGod
 * @create 2021-11-14-14:50
 */
@RestController
@Slf4j
@Api("发送邮件")
@RequestMapping("/email")
@AllArgsConstructor
@ConditionalOnProperty(name = "modules.email", havingValue = "true")
public class EmailController {


    private IEmailService emailService;


    @PassToken
    @PostMapping("/sendEmailTemplate")
    public Result sendEmail(EmailTemplate email){
        emailService.senEmail(email);
        return Result.success();
    }

    @PassToken
    @PostMapping("/sendEmailTemplateList")
    public Result sendEmail(@RequestBody List<EmailTemplate> email){
        emailService.senEmail(email);
        return Result.success();
    }

    @PassToken
    @PostMapping("/sendEmailTemplateList2")
    public Result sendEmail(@RequestParam(value = "email") List<String> email, EmailTemplate emailTemplate){
        log.info("发送给： {}, 人数： {}", email, email.size());
        emailService.senEmail(email, emailTemplate);
        return Result.success();
    }
}
