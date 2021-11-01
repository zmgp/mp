package com.shu.ming.mp.modules.article.service.impl;

import com.shu.ming.mp.modules.article.service.ArticleService;
import com.shu.ming.mp.modules.article.util.ESUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * @author JGod
 * @create 2021-10-11-11:06
 */
@ConditionalOnBean(ESUtil.class)
@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    ESUtil esUtil;
}
