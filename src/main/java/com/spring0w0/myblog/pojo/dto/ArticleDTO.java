package com.spring0w0.myblog.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class ArticleDTO {

    /**
     * 文章ID
     */
    private Integer id;
    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;
    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章状态 0-草稿 1-发布
     */
    private Integer status;

    /**
     * 标签ID列表
     */
    private List<Integer> tagIds;

    /**
     * 分类ID
     */
    private Integer categoryId;
}
