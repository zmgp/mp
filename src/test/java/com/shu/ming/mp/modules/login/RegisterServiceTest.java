package com.shu.ming.mp.modules.login;

import com.shu.ming.mp.MpApplication;
import com.shu.ming.mp.anno.MethodName;
import com.shu.ming.mp.commons.util.EmailUtil;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import com.shu.ming.mp.modules.login.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author JGod
 * @create 2021-10-13-13:17
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;


    @BeforeClass
    public static void initClass() {
    }


    @Before
    public void setup() {
       log.info("before");
    }

    @Test
    public void testRegister() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("1017459962@qq.com");
        registerDTO.setUsername("jie1");
        registerDTO.setPassword("1248231");
        EmailUtil.sendEmail("1017459962@qq.com","123","123321");
        registerService.insertOneUser(registerDTO);
    }



    @After
    public void end() {
        log.info("after");
    }


    @AfterClass
    public static void endClass() {
    }
}
