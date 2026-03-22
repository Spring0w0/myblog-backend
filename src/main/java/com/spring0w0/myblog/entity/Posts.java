package com.spring0w0.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
     * Markdown原文，必填
     */
    private String content;

    /**
     * 封面图路径
     */
    private String cover;

    /**
     * 标签，逗号分隔字符串
     */
    private String tags;

    /**
     * 分类，逗号分隔字符串
     */
    private String categories;

    /**
     * 文章状态：PUBLISHED（已发布）、DRAFT（草稿）
     */
    private String status;

    /**
     * 阅读量,初次新增文章时，数据库会默认设置为0
     */
    private Integer viewCount;

    /**
     * 创建时间，数据库会自动生成
     */
    @TableField("create_at")
    private LocalDateTime createAt;

    /**
     * 更新时间：数据库会自动更新
     */
    @TableField("update_at")
    private LocalDateTime updateAt;

}
