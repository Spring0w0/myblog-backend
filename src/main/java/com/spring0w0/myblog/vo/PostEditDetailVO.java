package com.spring0w0.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Spring0w0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEditDetailVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题，必填
     */
    private String title;

    /**
     * 文章摘要，用于列表展示
     */
    private String summary;

    /**
     * Markdown原文
     */
    private String content;

    /**
     * 封面图路径
     */
    private String cover;

    /**
     * 标签
     */
    private String tags;

    /**
     * 分类
     */
    private String categories;

    /**
     * 文章状态：PUBLISHED（已发布）、DRAFT（草稿）
     */
    private String status;


}
