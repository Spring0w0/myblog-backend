package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring0w0
 */
@TableName("category")
@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends BaseEntity {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类状态
     */
    private Integer status;

}

