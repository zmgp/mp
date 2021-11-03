package com.shu.ming.mp.commons.util;

import cn.hutool.extra.mail.MailUtil;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author JGod
 * @create 2021-10-15-15:33
 */
@Slf4j
@Component
public class EmailUtil {

    /**
     * 发送普通邮件
     */
    public static void sendEmail(String to, String subject, String content) {
        MailUtil.send(to, subject, content, false);
    }


}
