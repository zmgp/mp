package com.shu.ming.mp;

import com.maxmind.geoip2.DatabaseReader;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.commons.util.IPUtil;
import com.shu.ming.mp.commons.util.IdentifyCode;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AllArgsConstructor
class MpApplicationTests {
//    @Autowired
//    DatabaseReader databaseReader;
//    @Test
//    void contextLoads() throws Exception {
//        String ip = "113.118.96.221";
//        String address = IPUtil.getAddress(databaseReader,ip);
//        System.out.println("您的IP位置是："+address);
//    }


    @Test
    void sendEmail(){
        EmailUtil.sendEmail("1017459962@qq.com",  "jie测试", "hello");
    }


//    RedisBloomFilterService filterService;


    @Test
    public void contextLoas() throws Exception {

    }
}
