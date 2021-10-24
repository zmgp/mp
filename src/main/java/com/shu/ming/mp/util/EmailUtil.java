package com.shu.ming.mp.util;

import cn.hutool.extra.mail.MailUtil;

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
}
