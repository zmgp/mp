package com.shu.ming.mp.modules.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.ming.mp.modules.login.bean.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ForgetPWDMapper extends BaseMapper<UserInfo> {
    /*
     * 判断用户邮箱 是否存在 存在得到user
     */
    //@Select("select * from user_info where email = #{email}")
    UserInfo forgetUser(@Param("email") String email);

    /*
     *修改密码
     */
    @Update("update user_info set password = #{password} where username = #{username}")
    void updateOneUserPWD(@Param("password") String password,@Param("username") String username);
}
