package com.wengxs.dao;

import com.wengxs.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by Zorg
 * 2020-02-21 12:25
 */
public interface ArticleTagDao extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findAllByArticleId(Long articleId);

    void deleteAllByArticleId(Long articleId);

    @Query(
            value = "select tag, count(id) as total from ArticleTag group by tag order by total desc"
    )
    List<Map<String, Object>> findAllGroupByTag();

}
