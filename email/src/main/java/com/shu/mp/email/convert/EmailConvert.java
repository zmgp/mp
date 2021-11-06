package com.shu.mp.email.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.mp.email.po.EmailHistory;
import com.shu.mp.email.po.EmailJob;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author JGod
 * @create 2021-11-16-16:11
 */
public class EmailConvert {

    public static EmailHistory createEmailHistory(EmailTemplate emailTemplate){
        EmailHistory emailHistory = new EmailHistory();
        BeanUtil.copyProperties(emailTemplate, emailHistory);

        if (emailTemplate.getEmailType() == null){
            emailHistory.setTemplate(emailTemplate.getDesc());
        }else {
            emailHistory.setTemplate(emailTemplate.getEmailType().desc());
        }

        emailHistory.setSendTime(Timestamp.from(Instant.now()));
        return emailHistory;
    }


    public static EmailJob createEmailJobByDelay(EmailTemplate emailTemplate){
        EmailJob emailJob = new EmailJob();
        BeanUtil.copyProperties(emailTemplate, emailJob);

        if (emailTemplate.getDelay() != null){
            emailJob.setSendTime(System.currentTimeMillis() + emailTemplate.getDelay());
        }else {
            emailJob.setSendTime(emailTemplate.getSendTime());
        }

        emailJob.setTemplate(emailTemplate.getEmailType().desc());
        return emailJob;
    }


    public static EmailJob createEmailJobByTimer(EmailTemplate emailTemplate){
        EmailJob emailJob = new EmailJob();
        BeanUtil.copyProperties(emailTemplate, emailJob);

        if (emailTemplate.getSendTime() != null){
            emailJob.setSendTime(emailTemplate.getSendTime());
        }else {
            emailJob.setSendTime(System.currentTimeMillis() + emailTemplate.getDelay());
        }

        emailJob.setTemplate(emailTemplate.getEmailType().desc());
        return emailJob;
    }


    public static EmailTemplate createEmailTemplate(EmailJob emailJob){
        EmailTemplate emailTemplate = new EmailTemplate();
        BeanUtil.copyProperties(emailJob, emailTemplate);

        emailTemplate.setDesc(emailJob.getTemplate());

        return emailTemplate;
    }

    public static final String EMAIL_SPLIT = ",";

    public static EmailJob createEmailJobByTimer(List<String> emails, EmailTemplate emailTemplate){
        EmailJob emailJob = new EmailJob();
        BeanUtil.copyProperties(emailTemplate, emailJob);

        String send = emails.stream().reduce((e1, e2) -> e1.concat(EMAIL_SPLIT).concat(e2)).get();
        emailJob.setReceiveEmail(send);

        if (emailTemplate.getSendTime() != null){
            emailJob.setSendTime(emailTemplate.getSendTime());
        }else {
            emailJob.setSendTime(System.currentTimeMillis() + emailTemplate.getDelay());
        }

        emailJob.setTemplate(emailTemplate.getEmailType().desc());
        return emailJob;
    }

    public static EmailJob createEmailJobByDelay(List<String> emails, EmailTemplate emailTemplate){
        EmailJob emailJob = new EmailJob();
        BeanUtil.copyProperties(emailTemplate, emailJob);

        String send = emails.stream().reduce((e1, e2) -> e1.concat(EMAIL_SPLIT).concat(e2)).get();
        emailJob.setReceiveEmail(send);

        if (emailTemplate.getDelay() != null){
            emailJob.setSendTime(System.currentTimeMillis() + emailTemplate.getDelay());
        }else {
            emailJob.setSendTime(emailTemplate.getSendTime());
        }

        emailJob.setTemplate(emailTemplate.getEmailType().desc());
        return emailJob;
    }
}
