package com.wengxs.service;

import com.wengxs.entity.Article;

import java.util.List;

/**
 * Created by Zorg
 * 2020-02-21 12:38
 */
public interface ArticleService {

    Article find(Long id);

    Article save(Article article);

    void delete(Long id);

    List<Article> findRecentArticles(int number);
}
