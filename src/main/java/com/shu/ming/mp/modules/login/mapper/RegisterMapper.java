package com.shu.ming.mp.modules.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegisterMapper  extends BaseMapper<UserInfo> {
    /*
     *查询用户
     */
    @Select("select * from user_info where username = #{username}")
    UserInfo findUserByName(@Param("username") String username);


    /*
     *新增一个用户 不知道mybatis mapper.xml怎么配
     */
    void insertOneUser(UserInfo userInfo);
}
