package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Spring0w0
 */
@TableName("article_tag")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag extends BaseEntity {

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 标签ID
     */
    private Integer tagId;

}
