package com.shu.ming.mp;

import com.shu.ming.mp.util.IdentifyCode;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MpApplicationTests {

    @Test
    void contextLoads() {
        //根据用户邮箱发送邮件
        HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，qq邮箱为smtp.qq.com
        email.setCharset("utf-8");//设置发送的字符类型
        try {
            email.addTo("1017459962@qq.com");
            email.setFrom("1017459962@qq.com","ming");
            email.setAuthentication("1017459962@qq.com","fpzchkdyoravbdfg");//设置发送人的邮箱和授权码
            email.setSubject("mp平台用户注册码");//设置发送主题
            email.setMsg(IdentifyCode.code());//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }
    }

}
