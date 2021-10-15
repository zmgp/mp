package com.shu.ming.mp.modules.login.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author JGod
 * @create 2021-10-18-18:48
 */
@TableName("admin")
@Data
public class Demo {
    /**
     * 用户Id
     */
    private int id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;
}
