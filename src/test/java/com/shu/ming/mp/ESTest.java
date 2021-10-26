package com.shu.ming.mp;

import com.shu.ming.mp.modules.article.util.ESUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author JGod
 * @create 2021-10-11-11:21
 */
@SpringBootTest
public class ESTest {

    @Autowired
    ESUtil esUtil;

    @Test
    void load() throws IOException {
        CreateIndexResponse index = esUtil.createIndex("123");
        System.out.println(index);
    }
}
