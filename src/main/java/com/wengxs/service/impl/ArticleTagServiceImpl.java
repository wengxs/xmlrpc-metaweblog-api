package com.wengxs.service.impl;

import com.wengxs.dao.ArticleTagDao;
import com.wengxs.entity.ArticleTag;
import com.wengxs.entity.enums.ArticleStatus;
import com.wengxs.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Zorg
 * 2020-02-21 12:40
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public ArticleTag find(Long id) {
        return articleTagDao.getOne(id);
    }

    @Override
    public List<ArticleTag> findByArticle(Long articleId) {
        return articleTagDao.findAllByArticleId(articleId);
    }

    @Override
    public List<Map<String, Object>> groupByTag() {
        return articleTagDao.findAllGroupByTag();
    }

    @Override
    public ArticleTag save(ArticleTag articleTag) {
        return articleTagDao.save(articleTag);
    }

    @Override
    public void deleteByArticle(Long articleId) {
        articleTagDao.deleteAllByArticleId(articleId);
    }

}
