package com.wengxs.dao;

import com.wengxs.entity.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zorg
 * 2020-02-21 12:25
 */
public interface ArticleCategoryDao extends JpaRepository<ArticleCategory, Long> {

    ArticleCategory findByName(String name);

}
