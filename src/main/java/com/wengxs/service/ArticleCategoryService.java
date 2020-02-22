package com.wengxs.service;

import com.wengxs.entity.ArticleCategory;

import java.util.List;

/**
 * Created by Zorg
 * 2020-02-21 12:38
 */
public interface ArticleCategoryService {

    ArticleCategory find(Long id);

    ArticleCategory findByName(String name);

    ArticleCategory save(ArticleCategory articleCategory);

    List<ArticleCategory> findAll();

}
