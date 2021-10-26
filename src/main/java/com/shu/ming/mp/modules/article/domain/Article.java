package com.shu.ming.mp.modules.article.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author JGod
 * @create 2021-10-10-10:40
 */
@Data
public class Article {

    /**
     * 文章名称
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 优先级
     */
    private int level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否隐藏
     */
    private boolean hidden;

    /**
     * 内容
     */
    private String content;
}
