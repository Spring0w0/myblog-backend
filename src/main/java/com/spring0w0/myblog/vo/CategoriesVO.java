package com.spring0w0.myblog.vo;

import lombok.Data;


/**
 * @author Spring0w0
 */
@Data
public class CategoriesVO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类计数
     */
    private Integer count;

}
