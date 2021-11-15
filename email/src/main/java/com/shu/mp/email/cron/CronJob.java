package com.shu.mp.email.cron;

import com.shu.common.modules.email.domain.EmailTemplate;
import com.shu.mp.email.convert.EmailConvert;
import com.shu.mp.email.dao.EmailJobDAO;
import com.shu.mp.email.po.EmailJob;
import com.shu.mp.email.service.IEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JGod
 * @create 2021-10-19-19:29
 */
@Slf4j
@Component
@AllArgsConstructor
public class CronJob {

    private EmailJobDAO emailJobDAO;
    private IEmailService iEmailService;

    /**
     * 每十秒统计一次
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void emailDiscovery(){
        log.info("开始检索是否有定时或延时邮件");
        List<EmailJob> jobs = emailJobDAO.findAllBySendTimeBefore(System.currentTimeMillis());

        if (jobs.size() == 0){
            return;
        }

        List<EmailTemplate> emailTemplates = jobs.stream().map(EmailConvert::createEmailTemplate).collect(Collectors.toList());
        iEmailService.sendEmailCron(emailTemplates);
        emailJobDAO.deleteAllById(jobs.stream().map(EmailJob::getId).collect(Collectors.toList()));
    }

}
