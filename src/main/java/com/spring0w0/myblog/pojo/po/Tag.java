package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring0w0
 */
@TableName("tag")
@EqualsAndHashCode(callSuper = true)
@Data
public class Tag extends BaseEntity {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签状态
     */
    private Integer status;

}
