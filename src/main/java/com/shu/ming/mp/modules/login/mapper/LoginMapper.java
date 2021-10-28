package com.shu.ming.mp.modules.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.ming.mp.modules.login.bean.Authority;
import com.shu.ming.mp.modules.login.bean.Demo;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LoginMapper extends BaseMapper<UserInfo> {

    /*
     *查询用户是否存在 存在返回用户
     */
    @Select("select * from user_info where username = #{username} and password = #{password}")
    List<UserInfo> isExist(LoginDTO userInfo);

    /*
     *根据id查用户
     */
    UserInfo findUserById(@Param("id") int id );



}
