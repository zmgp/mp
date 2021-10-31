package com.shu.ming.mp.modules.login;

import com.shu.ming.mp.MpApplication;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import com.shu.ming.mp.modules.login.service.RegisterService;
import javafx.application.Application;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

/**
 * @author JGod
 * @create 2021-10-13-13:17
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterTest {

    @Autowired(required = false)
    private RegisterService registerService;


    @BeforeClass
    public static void initClass() {
    }


    @Before
    public void setup() {
    }

    @Test
    public void test() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setCode("4396");
        registerDTO.setEmail("110@qq.com");
        registerDTO.setUsername("jie");
        registerDTO.setPassword("124823");
        registerService.insertOneUser(registerDTO);
    }



    @After
    public void end() {
    }

    @AfterClass
    public static void endClass() {
    }
}
