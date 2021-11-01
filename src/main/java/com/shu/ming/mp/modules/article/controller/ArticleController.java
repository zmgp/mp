package com.shu.ming.mp.modules.article.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.shu.ming.mp.commons.annotation.PassToken;
import com.shu.ming.mp.commons.domain.Result;
import com.shu.ming.mp.modules.article.domain.Article;
import com.shu.ming.mp.modules.article.service.ArticleService;
import com.shu.ming.mp.modules.article.util.ESUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author JGod
 * @create 2021-10-11-11:05
 */
@ConditionalOnBean(ESUtil.class)
@Slf4j
@RestController
@RequestMapping("/article")
@Api("article")
@AllArgsConstructor
public class ArticleController {

    ArticleService articleService;
    ESUtil esUtil;

    @PassToken
    @PostMapping("/index")
    @ApiOperation("创建索引")
    public Result addIndex(String indexName) throws IOException {
        CreateIndexResponse response = esUtil.createIndex(indexName);
        return Result.success(response);
    }

    /*
    示例：
                {
                "name":"mp项目",
                "author": "ming-ping",
                "level": 1,
                "content": "this is a mp item, is very good"
                }

        目前： 不可用
     */

    @PassToken
    @PostMapping("/add")
    @ApiOperation("添加文章")
    public Result addArticle(@RequestBody Article article, String indexName){
        log.info("添加文章：{}", BeanUtil.beanToMap(article));
        esUtil.cache(BeanUtil.beanToMap(article), indexName);
        return Result.success();
    }

    @PassToken
    @PostMapping("/get")
    @ApiOperation("搜索文章")
    public Result search(String indexName, @RequestBody String queryString){
        log.info("是否存在该索引： {}", esUtil.existIndex(indexName));
        List<Map<String, Object>> result = esUtil.search(indexName, queryString);
        log.info("查询到的数目： {}", result.size());
        return Result.success(result);
    }

}
