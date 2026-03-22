package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
public class TagDetailVO {
    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
