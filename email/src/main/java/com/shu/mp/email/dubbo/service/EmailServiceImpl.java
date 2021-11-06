package com.shu.mp.email.dubbo.service;

import cn.hutool.extra.spring.SpringUtil;
import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.common.modules.email.domain.EmailType;
import com.shu.common.modules.email.service.EmailService;
import com.shu.mp.email.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-20-20:24
 */
@Service(version = "1.0")
@Slf4j
public class EmailServiceImpl implements EmailService {

    IEmailService iEmailService;

    public EmailServiceImpl(IEmailService iEmailService){
        this.iEmailService = SpringUtil.getBean(IEmailService.class);
    }

    @Override
    public void sendEmail(EmailTemplate send) {
        iEmailService.sendEmail(send);
    }

    @Override
    public void sendEmail(List<EmailTemplate> sends) {
        iEmailService.sendEmail(sends);
    }

    @Override
    public void sendEmail(List<String> emails, EmailTemplate emailTemplate) {
        iEmailService.sendEmail(emails, emailTemplate);
    }
}
