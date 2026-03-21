package com.spring0w0.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {

    /**
     * 文章标题，必填
     */
    private String title;

    /**
     * 文章摘要，用于列表展示
     */
    private String summary;

    /**
     * Markdown原文，必填
     */
    private String content;

    /**
     * 封面图路径
     */
    private String cover;

    /**
     * 标签数组，使用 JSON 格式存储
     */
    private List<String> tags;

    /**
     * 阅读时间，单位：分钟
     */
    private Integer readTime;
}
