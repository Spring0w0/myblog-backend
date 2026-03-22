package com.spring0w0.myblog.pojo.dto;

import lombok.Data;

/**
 * @author Spring0w0
 */
@Data
public class CategoryDTO {

    /**
     * 分类ID
     */
    private Integer id;
    /**
     * 分类状态(0:禁用；1:启用)
     */
    private Integer status;
    /**
     * 分类名称
     */
    private String name;

}
