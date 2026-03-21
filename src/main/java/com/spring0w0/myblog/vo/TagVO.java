package com.spring0w0.myblog.vo;

import lombok.Data;

/**
 * @author Spring0w0
 */
@Data
public class TagVO {

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签数
     */
    private Integer count;
}
