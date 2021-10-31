package com.shu.ming.mp.modules.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

/**
 * @author JGod
 * @create 2021-10-14-14:55
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testRegister() throws Exception {
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register/emailReg")
                        .header("token", "124823")
                        .param("mycode", "124823")
        );
        System.out.println(perform.andReturn().getResponse().getContentAsString(Charset.defaultCharset()));
    }
}
