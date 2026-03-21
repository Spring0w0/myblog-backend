package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.entity.Posts;
import com.spring0w0.myblog.vo.PostCardListVO;
import com.spring0w0.myblog.vo.PostDetailVO;

import java.util.List;

/**
 * @author Spring0w0
 */
public interface IPostsService extends IService<Posts> {

    /**
     * 获取文章列表，支持分页和筛选
     * @param page 页码
     * @param limit 每页数量
     * @param tags 标签数组
     * @param categories 分类
     * @return 文章列表
     */
    PostCardListVO getPosts(Integer page, Integer limit, List<String> tags, String categories);

    /**
     * 获取文章详细内容
     * @param id
     * @return 文章详细内容
     */
    PostDetailVO getPostDetail(long id);
}

