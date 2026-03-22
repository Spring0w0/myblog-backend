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
public class Categories {
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
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
