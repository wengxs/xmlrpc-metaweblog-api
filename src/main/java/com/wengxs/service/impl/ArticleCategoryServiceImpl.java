package com.wengxs.service.impl;

import com.wengxs.dao.ArticleCategoryDao;
import com.wengxs.entity.ArticleCategory;
import com.wengxs.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zorg
 * 2020-02-21 12:40
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Override
    public ArticleCategory find(Long id) {
        return articleCategoryDao.getOne(id);
    }

    @Override
    public ArticleCategory findByName(String name) {
        return articleCategoryDao.findByName(name);
    }

    @Override
    public List<ArticleCategory> findAll() {
        return articleCategoryDao.findAll();
    }

    @Override
    public ArticleCategory save(ArticleCategory articleCategory) {
        return articleCategoryDao.save(articleCategory);
    }

}
