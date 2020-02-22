package com.wengxs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Zorg
 * 2020-02-21 15:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bl_article_category")
public class ArticleCategory extends BaseEntity<Long> {

    @Column(unique = true, nullable = false, length = 32)
    private String name;

    @Column(columnDefinition = "varchar(32) null default''")
    private String url;

}
