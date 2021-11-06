package com.shu.ming.mp.modules.email.service;

import com.shu.common.modules.email.domain.EmailTemplate;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-21-21:54
 */
public interface IEmailService {

    void senEmail(EmailTemplate email) ;

    void senEmail(List<EmailTemplate> email) ;

    void senEmail(List<String> emails, EmailTemplate emailTemplate) ;
}
