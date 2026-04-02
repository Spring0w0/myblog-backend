package com.spring0w0.myblog.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
@Builder
public class StatsVO {

    /**
     * 文章总数
     */
    private  Integer articleTotal;

    /**
     * 标签总数
     */
    private Integer tagTotal;

    /**
     * 分类总数
     */
    private Integer categoryTotal;

    /**
     * 总阅读量
     */
    private Integer totalViewCount;

    /**
     * 最新文章日期
     */
    private LocalDateTime latestArticleTime;

}
