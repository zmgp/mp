package com.shu.ming.mp.modules.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shu.ming.mp.modules.login.bean.Demo;

import java.util.List;

/**
 * @author JGod
 * @create 2021-10-18-18:53
 */

public interface DemoService  extends IService<Demo> {

    List<Demo> self(int id);
}
