package com.spring0w0.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
public class Tags {

    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签数
     */
    private Integer count;

    /**
     * 创建时间
     */
    @TableField("create_at")
    public LocalDateTime createdAt;
}
