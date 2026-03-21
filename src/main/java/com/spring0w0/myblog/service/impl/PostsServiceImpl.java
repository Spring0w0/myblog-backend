package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.entity.Posts;
import com.spring0w0.myblog.mapper.PostsMapper;
import com.spring0w0.myblog.service.IPostsService;
import com.spring0w0.myblog.vo.PostCardListVO;
import com.spring0w0.myblog.vo.PostCardVO;
import com.spring0w0.myblog.vo.PostDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring0w0
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Override
    public PostCardListVO getPosts(Integer page, Integer limit, List<String> tags, String categories) {
        // 设置默认值
        page = ObjectUtil.defaultIfNull(page, 1);
        page = Math.max(page, 1);
        limit = ObjectUtil.defaultIfNull(limit, 6);
        limit = Math.max(limit, 1);

        // 构建查询条件
        LambdaQueryWrapper<Posts> queryWrapper = new LambdaQueryWrapper<>();
        
        // 标签筛选（OR关系）
        if (CollUtil.isNotEmpty(tags)) {
            queryWrapper.and(wrapper -> {
                for (String tag : tags) {
                    wrapper.or().like(Posts::getTags, tag);
                }
            });
        }
        
        // 分类筛选（精确匹配）
        if (StrUtil.isNotBlank(categories)) {
            queryWrapper.eq(Posts::getCategories, categories);
        }
        
        // 只查询已发布的文章
        queryWrapper.eq(Posts::getStatus, "PUBLISHED");
        
        // 按创建时间倒序
        queryWrapper.orderByDesc(Posts::getCreateAt);

        // 执行分页查询
        Page<Posts> postsPage = this.page(new Page<>(page, limit), queryWrapper);

        // 转换为VO
        List<PostCardVO> postCardVOList = CollUtil.newArrayList();
        for (Posts post : postsPage.getRecords()) {
            postCardVOList.add(BeanUtil.copyProperties(post, PostCardVO.class));
        }

        // 计算总页数
        long total = this.count(queryWrapper);
        int totalPages = total > 0 ? (int) ((total - 1) / limit + 1) : 0;

        // 构建返回结果
        PostCardListVO postCardListVO = new PostCardListVO();
        postCardListVO.setPosts(postCardVOList);
        postCardListVO.setTotal((int) total);
        postCardListVO.setPage(page);
        postCardListVO.setLimit(limit);
        postCardListVO.setTotalPages(totalPages);

        return postCardListVO;
    }

    @Override
    public PostDetailVO getPostDetail(long id) {
        Posts posts = this.getById(id);
        return BeanUtil.copyProperties(posts, PostDetailVO.class);
    }
}


