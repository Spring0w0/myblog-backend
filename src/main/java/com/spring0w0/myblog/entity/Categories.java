package com.spring0w0.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
public class Categories {
    /**
     * 分类id
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类计数
     */
    private Integer count;

    /**
     * 创建时间
     */
    @TableField("create_at")
    public LocalDateTime createdAt;
}
