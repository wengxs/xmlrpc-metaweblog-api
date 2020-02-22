package com.wengxs.service.impl;

import com.wengxs.dao.ArticleDao;
import com.wengxs.dao.ArticleTagDao;
import com.wengxs.entity.Article;
import com.wengxs.entity.ArticleTag;
import com.wengxs.entity.enums.ArticleStatus;
import com.wengxs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zorg
 * 2020-02-21 12:40
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public Article find(Long id) {
        return articleDao.getOne(id);
    }

    @Override
    @Transactional
    public Article save(Article article) {
        article = articleDao.save(article);
        articleTagDao.deleteAllByArticleId(article.getId());
        if (ArticleStatus.publish.getCode() == article.getStatus() && StringUtils.hasText(article.getKeywords())) {
            String[] tags = article.getKeywords().split(",");
            for (String tag : tags) {
                ArticleTag at = new ArticleTag();
                at.setTag(tag);
                at.setArticleId(article.getId());
                articleTagDao.save(at);
            }
        }
        return article;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Article article = find(id);
        article.setStatus(ArticleStatus.deleted.getCode());
        articleDao.save(article);
        articleTagDao.deleteAllByArticleId(id);
    }

    @Override
    public List<Article> findRecentArticles(int number) {
        Page<Article> articles = articleDao.findAllByStatusIn(
                Arrays.asList(ArticleStatus.draft.getCode(), ArticleStatus.publish.getCode()),
                PageRequest.of(0, number, Sort.by(Sort.Order.desc("createTime")))
        );
        return articles.getContent();
    }

}
