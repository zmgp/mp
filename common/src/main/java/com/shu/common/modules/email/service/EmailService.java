package com.shu.common.modules.email.service;

import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.common.modules.email.domain.EmailType;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-20-20:23
 */
public interface EmailService {

    void sendEmail(EmailTemplate send);

    void sendEmail(List<EmailTemplate> sends);

    void sendEmail(List<String> emails, EmailTemplate emailTemplate);

}
