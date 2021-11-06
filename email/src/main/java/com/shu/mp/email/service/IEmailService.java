package com.shu.mp.email.service;

import com.shu.common.modules.email.domain.EmailTemplate;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-15-15:03
 */
public interface IEmailService {

    void sendEmail(EmailTemplate emailTemplate);

    void sendEmail(List<EmailTemplate> emailTemplates);

    void sendEmail(List<String> emails, EmailTemplate emailTemplate);

    void sendEmailCron(List<EmailTemplate> emailTemplates);
}
