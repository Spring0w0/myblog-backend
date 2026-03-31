package com.spring0w0.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring0w0.myblog.pojo.po.ArticleTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Spring0w0
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    @Select("<script>" +
            "SELECT article_id FROM article_tag " +
            "WHERE tag_id IN " +
            "<foreach collection='tagIds' item='id' open='(' separator=',' close=')'>#{id}</foreach> " +
            "GROUP BY article_id " +
            "HAVING COUNT(DISTINCT tag_id) = #{tagCount}" +
            "</script>")
    List<Integer> selectArticleIdsWithAllTags(@Param("tagIds") List<Integer> tagIds, @Param("tagCount") int tagCount);
}
