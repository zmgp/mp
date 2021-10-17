package com.shu.ming.mp.modules.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.modules.login.bean.Authority;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.mapper.AuthorityMapper;
import com.shu.ming.mp.modules.login.mapper.LoginMapper;
import com.shu.ming.mp.modules.login.service.AuthorityService;
import com.shu.ming.mp.modules.login.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author JGod
 * @create 2021-10-18-18:20
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {


    @Override
    public List<Integer> getAuthorityByUserId(int userId) {
        List<Authority> authorityList = findAuthorityByUserId(userId);
        return authorityList.stream().map(Authority::getAuthority).collect(Collectors.toList());
    }


    /**
     *  根据用户Id获取该用户的权限
     *  @param userId
     *  @return
     */
    private List<Authority> findAuthorityByUserId(int userId) {
        QueryWrapper<Authority> query = new QueryWrapper<>();
        query
                .eq("user_id", userId);
        return this.list(query);
    }
}
