package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class ArticleCardVO {

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
     * 文章摘要
     */
    private String summary;

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
