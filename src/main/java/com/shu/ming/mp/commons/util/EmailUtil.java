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

    public static String emailPd;
    public static String emailHost;
    public static String hostname;
    public static String charset;
    @Value("${emailHost}")
    public void setEmailHost(String emailHost) {
        log.info("设置emailHost为: {} " , emailHost);
        EmailUtil.emailHost = emailHost;
    }

    @Value("${expire}")
    public void setEmailPd(String emailPd) {
        log.info("设置邮箱授权码为: {} ",  emailPd);
        EmailUtil.emailPd = emailPd;
    }
    @Value("${hostname}")
    public void setHostname(String hostname) {
        log.info("设置邮箱smtp服务器为: {} ",  hostname);
        EmailUtil.hostname = hostname;
    }
    @Value("${charset}")
    public void setCharset(String charset) {
        log.info("设置邮箱发送字符类型9为: {} ",  charset);
        EmailUtil.charset = charset;
    }
    /**
     * 发送普通邮件
     */
    public static void sendEmail(String to, String subject, String content) {
        MailUtil.send(to, subject, content, false);
    }

    /*
     * 发送注册邮件
     */
    public static void sendRegisterEmail(RegisterDTO registerDTO,String code){
        //根据用户邮箱发送邮件
        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName(hostname);//邮箱的SMTP服务器，qq邮箱为smtp.qq.com
        email.setCharset(charset);//设置发送的字符类型
        try {
            email.addTo(registerDTO.getEmail());
            email.setFrom(emailHost,emailHost);
            email.setAuthentication(emailHost,emailPd );//设置发送人的邮箱和授权码
            email.setSubject("mp平台用户注册码");//设置发送主题
            email.setMsg(code);//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            log.info("邮箱发送失败");
            e.printStackTrace();
        }
    }
}
