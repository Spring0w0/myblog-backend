package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.*;

/**
 * @author Spring0w0
 */
@TableName("article")
@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {

    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章封面url
     */
    private String cover;
    /**
     * 文章状态 0-禁用 1-发布
     */
    private Integer status;
    /**
     * 阅读量
     */
    private Integer viewCount;
    /**
     * 分类ID
     */
    private Integer categoryId;
}
