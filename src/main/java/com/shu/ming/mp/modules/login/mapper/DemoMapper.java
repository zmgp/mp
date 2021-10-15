package com.shu.ming.mp.modules.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.ming.mp.modules.login.bean.Demo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author JGod
 * @create 2021-10-18-18:50
 */
public interface DemoMapper extends BaseMapper<Demo> {

    @Select("select * from admin where id = #{id}")
    List<Demo> self(@Param("id") int id);
}
