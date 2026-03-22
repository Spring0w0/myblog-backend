package com.spring0w0.myblog.pojo.dto;

import lombok.Data;

/**
 * @author Spring0w0
 */
@Data
public class TagDTO {

    /**
     * 标签ID
     */
    private Integer id;
    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签状态
     */
    private Integer status;
}
