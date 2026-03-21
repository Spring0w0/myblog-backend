package com.spring0w0.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCardVO {

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
     * 封面图路径
     */
    private String cover;

    /**
     * 标签（逗号分隔的字符串）
     */
    private String tags;

    /**
     * 状态 PUBLISHED-公开 DRAFT-草稿
     */
    private String status;

    /**
     * 分类
     */
    private String categories;

    /**
     * 阅读次数
     */
    private Integer viewCount;

    /**
     * 创建时间，数据库会自动生成
     */
    private LocalDateTime createAt;

    /**
     * 更新时间：数据库会自动更新
     */
    private LocalDateTime updateAt;
}
