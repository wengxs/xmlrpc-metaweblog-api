package com.wengxs.dao;

import com.wengxs.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Zorg
 * 2020-02-21 12:25
 */
public interface ArticleDao extends JpaRepository<Article, Long> {

    Page<Article> findAllByStatusIn(List<Integer> status, Pageable pageable);
}
