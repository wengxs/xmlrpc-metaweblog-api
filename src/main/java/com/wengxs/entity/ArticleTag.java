package com.wengxs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章标签
 * Created by Zorg
 * 2020/02/21 16:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bl_article_tag")
public class ArticleTag extends BaseEntity<Long> {

    @Column(nullable = false, length = 32)
    private String tag;

    @Column(nullable = false)
    private Long articleId;

}
