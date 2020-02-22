package com.wengxs.xmlrpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wengxs.entity.Article;
import com.wengxs.entity.ArticleCategory;
import com.wengxs.entity.enums.ArticleStatus;
import com.wengxs.service.AdminService;
import com.wengxs.service.ArticleCategoryService;
import com.wengxs.service.ArticleService;
import com.wengxs.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.common.XmlRpcNotAuthorizedException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * MetaWeblog Api实现
 * Created by Zorg
 * 2020-02-21 19:05
 */
@Slf4j
public class MetaWeblogImpl implements MetaWeblog {

    private AdminService adminService;

    private ArticleService articleService;

    private ArticleCategoryService articleCategoryService;

    {
        adminService = SpringBeanUtil.getBean(AdminService.class).orElse(null);
        articleService = SpringBeanUtil.getBean(ArticleService.class).orElse(null);
        articleCategoryService = SpringBeanUtil.getBean(ArticleCategoryService.class).orElse(null);
    }

    private void isValid(String username, String password) throws XmlRpcNotAuthorizedException {
        log.info("username: {}, password: {}", username, password);
        if (!adminService.isValid(username, password))
            throw new XmlRpcNotAuthorizedException("账号或密码有误");
    }

    @Override
    public List<Map<String, Object>> getUsersBlogs(String appKey, String username, String password) throws XmlRpcException {
        log.info("[blogger.getUsersBlogs] -> appKey: {}, username: {}, password: {}", appKey, username, password);
        isValid(username, password);

        List<Map<String, Object>> usersBlogs = new ArrayList<>();
        Map<String, Object> blogInfo = new HashMap<>();
        blogInfo.put("blogid", "");
        blogInfo.put("url", "");
        blogInfo.put("blogName", "");
        usersBlogs.add(blogInfo);

        return usersBlogs;
    }

    private ArticleCategory getCategory(JSONArray categories) throws XmlRpcException {
        ArticleCategory ac;
        if (categories.size() != 1) {
            throw new XmlRpcException("必须或只能选择一个分类");
        } else {
            ac = articleCategoryService.findByName(categories.getString(0));
            if (ac == null) throw new XmlRpcException("分类信息不存在");
        }
        return ac;
    }

    private void transToArticle(Article article, JSONObject postJson) throws XmlRpcException {
        ArticleCategory ac = getCategory(postJson.getJSONArray("categories"));
        article.setCategory(ac);
        article.setTitle(postJson.getString("title"));
        article.setContent(postJson.getString("description"));
        article.setKeywords(postJson.getString("mt_keywords"));
        article.setAlias(postJson.getString("wp_slug"));
        article.setStatus("publish".equals(postJson.getString("post_status"))
                ? ArticleStatus.publish.getCode() : ArticleStatus.draft.getCode());
        article.setPostTime(postJson.getDate("dateCreated"));
    }

    private Map<String, Object> transToPost(Article article) {
        Map<String, Object> post = new HashMap<>();
        post.put("dateCreated", article.getPostTime().getTime() + "");
        post.put("description", article.getContent());
        post.put("title", article.getTitle());
        post.put("categories", Arrays.asList(article.getCategory().getName()));
        post.put("postid", article.getId() + "");
        post.put("mt_excerpt", article.getExcerpt());
        post.put("mt_keywords", article.getKeywords());
        post.put("wp_slug", article.getAlias());
        return post;
    }

    @Override
    public String newPost(String blogid, String username, String password,
                          Map<String, Object> post, boolean publish) throws XmlRpcException {
        log.info("metaWeblog.newPost -> blogid: {}, post: {}, publish: {}", blogid, JSON.toJSONString(post), publish);
        isValid(username, password);

        JSONObject postJson = JSONObject.parseObject(JSON.toJSONString(post));
        Article article = new Article();
        transToArticle(article, postJson);
        articleService.save(article);

        return article.getId() + "";
    }

    @Override
    public boolean editPost(String postid, String username, String password, Map<String, Object> post, boolean publish) throws XmlRpcException {
        log.info("metaWeblog.editPost -> postid: {}, post: {}", postid, JSON.toJSONString(post));
        isValid(username, password);

        JSONObject postJson = JSONObject.parseObject(JSON.toJSONString(post));
        Article article = articleService.find(Long.parseLong(postid));
        transToArticle(article, postJson);
        articleService.save(article);

        return true;
    }

    @Override
    public Map<String, Object> getPost(String postid, String username, String password) throws XmlRpcException {
        log.info("metaWeblog.getPost -> postid: {}", postid);
        isValid(username, password);

        Article article = articleService.find(Long.parseLong(postid));

        return transToPost(article);
    }

    @Override
    public List<Map<String, String>> getCategories(String blogid, String username, String password) throws XmlRpcException {
        log.info("metaWeblog.getCategories -> blogid: {}", blogid);
        isValid(username, password);

        List<ArticleCategory> articleCategories = articleCategoryService.findAll();
        List<Map<String, String>> res = new ArrayList<>();
        articleCategories.forEach(articleCategory -> {
            Map<String, String> map = new HashMap<>();
            map.put("description", articleCategory.getName());
            map.put("title", articleCategory.getName());
            map.put("htmlUrl", articleCategory.getUrl());
            res.add(map);
        });

        return res;
    }

    @Override
    public List<Map<String, Object>> getRecentPosts(String blogid, String username, String password, int numberOfPosts) throws XmlRpcException {
        log.info("metaWeblog.getRecentPosts -> blogid: {}, numberOfPosts: {}", blogid, numberOfPosts);
        isValid(username, password);

        List<Article> articles = articleService.findRecentArticles(numberOfPosts);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Article article : articles) {
            Map<String, Object> map = transToPost(article);
            maps.add(map);
        }

        return maps;
    }

    @Override
    public Map<String, String> newMediaObject(String blogid, String username, String password, Map<String, Object> post) throws XmlRpcException {
        log.info("metaWeblog.newMediaObject -> blogid: {}, post: {}", blogid, JSON.toJSONString(post));
        isValid(username, password);

        Map<String, String> urlData = new HashMap<>();
        String retUrl = "/upload/%s";

        String name = post.get("name").toString();
        byte[] bits = (byte[]) post.get("bits");

        File file;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        String path = "/Users/zorg/DevWorkspace/static/upload/";
        String fileName = UUID.randomUUID() + "-" + name;
        try {
            file = new File(path + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bits);
            bos.close();
            urlData.put("url", String.format(retUrl, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return urlData;
    }

}
