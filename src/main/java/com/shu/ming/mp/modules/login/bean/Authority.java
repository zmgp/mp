package com.shu.ming.mp.modules.login.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("authority")
@Data
public class Authority {
    private int id;
    @TableField("user_id")
    private int userid;
    private int authority;
}
