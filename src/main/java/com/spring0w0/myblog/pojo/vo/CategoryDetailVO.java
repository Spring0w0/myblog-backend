package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
public class CategoryDetailVO {

    /**
     * 分类ID
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
