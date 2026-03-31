package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
public class ArticleCardListVO {

    /**
     * 文章总数
     */
    private Integer total;

    /**
     * 文章列表
     */
    private List<ArticleCardVO> list;
}
