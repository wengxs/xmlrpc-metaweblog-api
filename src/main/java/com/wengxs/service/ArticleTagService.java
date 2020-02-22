package com.wengxs.service;

import com.wengxs.entity.ArticleTag;

import java.util.List;
import java.util.Map;

/**
 * Created by Zorg
 * 2020-02-21 12:38
 */
public interface ArticleTagService {

    ArticleTag find(Long id);

    List<ArticleTag> findByArticle(Long articleId);

    List<Map<String, Object>> groupByTag();

    ArticleTag save(ArticleTag articleTag);

    void deleteByArticle(Long articleId);

}
