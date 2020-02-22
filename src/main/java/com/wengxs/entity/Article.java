package com.wengxs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 博客文章表
 * Created by Zorg
 * 2020-02-21 15:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bl_article")
@DynamicInsert
@Proxy(lazy = false)
public class Article extends BaseEntity<Long> {

    /** 标题 */
    @Column(nullable = false, length = 128)
    private String title;

    /** 内容 */
    @Column(nullable = false, length = 1024 * 6)
    private String content;

    /** 分类ID */
    @JoinColumn(name = "category_id",nullable = false)
    @ManyToOne
    private ArticleCategory category;

    /** 摘要 */
    @Column(columnDefinition = "varchar(512) null default ''")
    private String excerpt;

    /** 关键字，标签 */
    @Column(columnDefinition = "varchar(128) null default ''")
    private String keywords;

    /** 别名 */
    @Column(nullable = false, length = 128)
    private String alias;

    /** 状态 */
    @Column(columnDefinition = "int not null default 0")
    private Integer status;

    /** 是否置顶 */
    @Column(columnDefinition = "int not null default 0")
    private Integer isTopping;

    /** 阅读量 */
    @Column(columnDefinition = "int not null default 0")
    private Integer viewCount;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private Date postTime;

}
