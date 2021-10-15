package com.shu.ming.mp.modules.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shu.ming.mp.modules.login.bean.Demo;
import com.shu.ming.mp.modules.login.mapper.DemoMapper;
import com.shu.ming.mp.modules.login.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JGod
 * @create 2021-10-18-18:53
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

    private DemoMapper demoMapper;

    /**
     * 使用构造器的方式注入
     * @param demoMapper
     */
    public DemoServiceImpl(DemoMapper demoMapper){
        this.demoMapper = demoMapper;
    }

    /**
     * 自定义实现
     * @return
     */
    @Override
    public List<Demo> self(int id) {
        // todo 业务逻辑
        return demoMapper.self(id);
    }
}
