package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class CategoryPageVO {

    /**
     * 总数
     */
    private Integer total;
    /**
     * 分类列表
     */
    private List<CategoryDetailVO> list;
}
