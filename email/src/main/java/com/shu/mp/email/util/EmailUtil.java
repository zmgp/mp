package com.shu.mp.email.util;

import cn.hutool.extra.mail.MailUtil;
import com.shu.common.modules.email.domain.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JGod
 * @create 2021-10-15-15:33
 */
public class EmailUtil {

    /**
     * 发送普通邮件
     */
    public static void sendEmail(String to, String subject, String content) {
        MailUtil.send(to, subject, content, false);
    }

    /**
     * 发送普通邮件
     */
    public static void sendEmail(EmailTemplate emailTemplate) {
        MailUtil.send(emailTemplate.getReceiveEmail(), emailTemplate.getSubject(), emailTemplate.getContent(), false);
    }

    /**
     * 群发邮件
     */
    public static void sendEmailList(List<String> emails, EmailTemplate emailTemplate){
        MailUtil.send(emails, emailTemplate.getSubject(), emailTemplate.getContent(), false);
    }


    private static final String VERIFICATION_CODE_SUBJECT = "注册验证码";
    private static final String VERIFICATION_CODE_CONTENT_PREFIX = "验证码: ";
    private static final String VERIFICATION_CODE_CONTENT_SUFFIX = ",有效期为15分钟。";
    /**
     * 发送邮件验证码
     */
    public static void sendVerificationCode(String email, String verificationCode){
        sendEmail(email, VERIFICATION_CODE_SUBJECT, VERIFICATION_CODE_CONTENT_PREFIX.concat(verificationCode).concat(VERIFICATION_CODE_CONTENT_SUFFIX));
    }
}
