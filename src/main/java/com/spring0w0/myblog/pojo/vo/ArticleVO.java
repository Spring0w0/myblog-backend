package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class ArticleVO {

    /**
     * 文章ID
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面url
     */
    private String cover;

    /**
     * 文章状态 0-禁用 1-发布
     */
    private Integer status;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 标签列表
     */
    private List<TagVO> tag;

    /**
     * 分类信息
     */
    private CategoryVO category;














}
