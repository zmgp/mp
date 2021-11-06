package com.shu.mp.email.service.impl;

import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.mp.email.convert.EmailConvert;
import com.shu.mp.email.dao.EmailHistoryDAO;
import com.shu.mp.email.dao.EmailJobDAO;
import com.shu.mp.email.po.EmailJob;
import com.shu.mp.email.service.IEmailService;
import com.shu.mp.email.util.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author JGod
 * @create 2021-11-15-15:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class IEmailServiceImpl implements IEmailService {

    EmailHistoryDAO emailHistoryDAO;

    EmailJobDAO emailJobDAO;


    @Override
    public void sendEmail(EmailTemplate emailTemplate) {
        switch (emailTemplate.getEmailType())
        {
            case SINGLE:
                sendSingle(emailTemplate);
                break;
            case SINGLE_DELAY:
                sendSingleDelay(emailTemplate);
                break;
            case SINGLE_TIMER:
                sendSingleTimer(emailTemplate);
                break;
            default:
                log.error("发送邮件类型错误，{}", emailTemplate);
        }
    }

    @Override
    public void sendEmail(List<EmailTemplate> emailTemplates) {
        for (EmailTemplate emailTemplate : emailTemplates) {
            sendEmail(emailTemplate);
        }
    }

    @Override
    public void sendEmail(List<String> emails, EmailTemplate emailTemplate) {

        switch (emailTemplate.getEmailType())
        {
            case LIST:
                sendList(emails, emailTemplate);
                break;
            case LIST_DELAY:
                sendListDelay(emails, emailTemplate);
                break;
            case LIST_TIMER:
                sendListTimer(emails, emailTemplate);
                break;
            default:
                log.error("发送邮件类型错误，{}", emailTemplate);
        }
    }

    @Override
    public void sendEmailCron(List<EmailTemplate> emailTemplates) {
        for (EmailTemplate emailTemplate : emailTemplates) {
            if (emailTemplate.getReceiveEmail().contains(EmailConvert.EMAIL_SPLIT)){
                List<String> emails = Arrays.asList(emailTemplate.getReceiveEmail().split(EmailConvert.EMAIL_SPLIT));
                sendList(emails, emailTemplate);
                return;
            }
            sendSingle(emailTemplate);
        }
    }

    /**
     * 多个即时发送
     */
    @Async
    public void sendList(List<String> emails, EmailTemplate emailTemplate){
        log.info("开始向 {} 发送邮件", emails);
        EmailUtil.sendEmailList(emails, emailTemplate);
        for (String email : emails) {
            emailTemplate.setReceiveEmail(email);
            saveEmailHistory(emailTemplate);
        }
    }

    /**
     * 多个 定时发送
     * @param emailTemplate
     */
    private void sendListTimer(List<String> emails, EmailTemplate emailTemplate){
        log.info("收到一个多人定时发送： {}", emailTemplate);
        saveEmailJob(EmailConvert.createEmailJobByTimer(emails, emailTemplate));
    }

    /**
     * 多个 延时发送
     * @param emailTemplate
     */
    private void sendListDelay(List<String> emails, EmailTemplate emailTemplate){
        log.info("收到一个多人延时发送： {}", emailTemplate);
        saveEmailJob(EmailConvert.createEmailJobByDelay(emails, emailTemplate));
    }

    /**
     * 单个 即时发送
     * @param emailTemplate
     */
    private void sendSingle(EmailTemplate emailTemplate){
        log.info("收到一个单人即时发送： {}", emailTemplate);
        send(emailTemplate);
    }

    /**
     * 单个 定时发送
     * @param emailTemplate
     */
    private void sendSingleTimer(EmailTemplate emailTemplate){
        log.info("收到一个单人定时发送： {}", emailTemplate);
        saveEmailJob(EmailConvert.createEmailJobByTimer(emailTemplate));
    }

    /**
     * 单个 延时发送
     * @param emailTemplate
     */
    private void sendSingleDelay(EmailTemplate emailTemplate){
        log.info("收到一个单人延时发送： {}", emailTemplate);
        saveEmailJob(EmailConvert.createEmailJobByDelay(emailTemplate));
    }



    @Async
    public void send(EmailTemplate emailTemplate){
        log.info("开始向 {} 发送邮件", emailTemplate.getReceiveEmail());
        EmailUtil.sendEmail(emailTemplate);

        saveEmailHistory(emailTemplate);
    }

    private void saveEmailHistory(EmailTemplate emailTemplate) {
        emailHistoryDAO.save(EmailConvert.createEmailHistory(emailTemplate));
    }

    private void saveEmailJob(EmailJob emailJob){
        emailJobDAO.save(emailJob);
    }
}
