package com.shu.ming.mp.modules.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shu.ming.mp.modules.login.bean.Authority;
import com.shu.ming.mp.modules.login.bean.Demo;

import java.util.List;

/**
 * @author JGod
 * @create 2021-10-18-18:20
 */
public interface AuthorityService extends IService<Authority> {

    List<Integer> getAuthorityByUserId(int userId);
}
