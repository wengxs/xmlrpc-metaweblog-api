package com.wengxs.entity.enums;

/**
 * 文章状态
 * Created by Zorg
 * 2020-02-21 16:26
 */
public enum ArticleStatus {
    draft(0, "草稿"),
    publish(1, "已发布"),
    deleted(2, "已删除");

    private int code;
    private String desc;
    ArticleStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
