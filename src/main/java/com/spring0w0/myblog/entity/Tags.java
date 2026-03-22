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
public class Tags {

    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
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
