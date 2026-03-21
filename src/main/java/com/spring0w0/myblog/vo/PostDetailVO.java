package com.spring0w0.myblog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class PostDetailVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题，必填
     */
    private String title;

    /**
     * Markdown原文
     */
    private String content;

    /**
     * 标签
     */
    private String tags;

    /**
     * 分类
     */
    private String categories;


}
