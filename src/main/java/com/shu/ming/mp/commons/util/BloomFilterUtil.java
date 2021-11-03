package com.shu.ming.mp.commons.util;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author JGod
 * @create 2021-10-16-16:17
 */
@ConditionalOnBean({RedisUtil.class})
@Component
@Slf4j
public class BloomFilterUtil {

    RedisUtil redisUtil;


    public BloomFilterUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    /**
     * 布隆过滤器
     */
    private static BitMapBloomFilter EmailFilter = new BitMapBloomFilter(20);
    private static BitMapBloomFilter NameFilter = new BitMapBloomFilter(20);

    /**
     * 前缀
     */
    public static final String EMAIL_PREFIX = "bloom:email:";
    public static final String NAME_PREFIX = "bloom:name:";

    @PostConstruct
    public void init() {
        log.info("开始初始化 布隆过滤器");
        initEmailBloom();
        initNameBloom();
        log.info("布隆过滤器初始化完成");
    }

    public  boolean containsName(String key){
        return NameFilter.contains(key);
    }

    public  boolean containsEmail(String key){
        return EmailFilter.contains(key);
    }

    public  void addEmailKey(String key){
        EmailFilter.add(key);
        redisUtil.set(EMAIL_PREFIX + key, key);
    }

    public void addNameKey(String key) {
        NameFilter.add(key);
        redisUtil.set(NAME_PREFIX + key, key);
    }

    public void addKey(RegisterDTO registerDTO) {
        addNameKey(registerDTO.getUsername());
        addEmailKey(registerDTO.getEmail());
    }

    private void initEmailBloom(){
        Set<String> keys = redisUtil.keys(EMAIL_PREFIX .concat("*"));
        List<String> list = redisUtil.multiGet(keys);
        list.parallelStream().forEach(
                this::addEmailKey
        );
    }

    private void initNameBloom(){
        Set<String> keys = redisUtil.keys(NAME_PREFIX .concat("*"));
        List<String> list = redisUtil.multiGet(keys);
        list.parallelStream().forEach(
                this::addNameKey
        );
    }
}
