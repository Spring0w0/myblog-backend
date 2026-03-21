package com.spring0w0.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Spring0w0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCardListVO {

    /**
     * 文章卡片列表
     */
    private List<PostCardVO> posts;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 分页参数
     */
    private Integer page;

    /**
     * 分页大小
     */
    private Integer limit;

    /**
     * 总页数
     */
    private Integer totalPages;

}
