package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.dto.ArticleDTO;
import com.spring0w0.myblog.pojo.po.Article;
import com.spring0w0.myblog.pojo.vo.ArticlePageVO;

/**
 * @author Spring0w0
 */
public interface IArticleService extends IService<Article> {
    /**
     * 添加文章
     * @param articleDTO 文章信息
     * @return 添加结果
     */
    boolean addArticle(ArticleDTO articleDTO);

    /**
     * 修改文章
     * @param id 文章ID
     * @param articleDTO 文章信息
     * @return 修改结果
     */
    boolean updateArticle(Integer id, ArticleDTO articleDTO);

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    boolean deleteArticle(Integer id);

    /**
     * 根据ID获取文章
     * @param id 文章ID
     * @return 文章信息
     */
    ArticleDTO getArticleById(Integer id);

    /**
     * 获取文章分页
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param status 文章状态
     * @param title 文章标题
     * @return 文章分页信息
     */
    ArticlePageVO getArticlePage(Integer page, Integer pageSize, Integer status, String title);

}
