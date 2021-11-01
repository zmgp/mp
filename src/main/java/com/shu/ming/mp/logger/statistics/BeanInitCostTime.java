package com.shu.ming.mp.logger.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JGod
 * @create 2021-10-14-14:18
 */
@Component
@ConditionalOnProperty(value = "mp.log", havingValue = "true", matchIfMissing = true)
@Slf4j
public class BeanInitCostTime implements BeanPostProcessor {

    private static final ConcurrentHashMap<String, Long> START_TIME = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        START_TIME.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.nonNull(START_TIME.get(beanName))){
            log.info("beanName: {}, cost: {} ms", bean, System.currentTimeMillis() - START_TIME.get(beanName));
        }
        return bean;
    }
}
