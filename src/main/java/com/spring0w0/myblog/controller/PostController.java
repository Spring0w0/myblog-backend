package com.spring0w0.myblog.controller;

import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.service.IPostsService;
import com.spring0w0.myblog.vo.PostCardListVO;
import com.spring0w0.myblog.vo.PostDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final IPostsService postsService;

    /**
     * 获取文章列表，支持分页和筛选
     * @param page 页码，默认1
     * @param limit 每页数量，默认6
     * @param tags 标签数组（多个同名参数）
     * @param categories 分类
     * @return 文章列表
     */
    @GetMapping
    public Message<PostCardListVO> getPosts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "categories", required = false) String categories) {
        
        PostCardListVO postCardListVO = postsService.getPosts(page, limit, tags, categories);
        return Message.success(postCardListVO, "成功");
    }

    /**
     * 根据id获取文章详情
     * @param id
     * @return 文章详细
     */
    @GetMapping("/{id}")
    public Message<PostDetailVO> getPostDetail(
            @PathVariable long id
    ){
        PostDetailVO postDetailVO = postsService.getPostDetail(id);
        return Message.success(postDetailVO, "成功");
    }

}

