package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class UserTagPageVO {

    /**
     * 标签总数
     */
    private Integer total;

    /**
     * 标签列表
     */
    private List<TagVO> list;
}
