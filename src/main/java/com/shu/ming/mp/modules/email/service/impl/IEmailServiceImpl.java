package com.shu.ming.mp.modules.email.service.impl;

import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.common.modules.email.service.EmailService;
import com.shu.ming.mp.modules.email.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-21-21:54
 */
@Slf4j
@ConditionalOnProperty(name = "modules.email", havingValue = "true")
@Service
public class IEmailServiceImpl implements IEmailService {

    @Reference(version = "1.0", timeout=50000, check = false)
    private EmailService emailService;


    @Async
    @Override
    public void senEmail(EmailTemplate email) {
        emailService.sendEmail(email);
    }

    @Async
    @Override
    public void senEmail(List<EmailTemplate> email) {
        emailService.sendEmail(email);
    }

    @Async
    @Override
    public void senEmail(List<String> emails, EmailTemplate emailTemplate) {
        emailService.sendEmail(emails, emailTemplate);
    }

}
