package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.*;
import com.spring0w0.myblog.service.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class UserArticleController {

    private final IArticleService articleService;

    /**
     * 获取文章卡片分页
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param tagIds 标签ID列表
     * @param categoryId 分类ID
     * @return 文章卡片分页信息
     */
    @GetMapping("/page")
    public Message<ArticleCardListVO> getArticlePage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam(required = false) List<Integer> tagIds,
            @RequestParam(required = false) Integer categoryId
    ) {
        ArticleCardListVO result = articleService.getArticleCardPage(page, pageSize, tagIds, categoryId);
        return Message.success(result);
    }

    /**
     * 根据ID获取文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/{id}")
    public Message<ArticleDetailVO> getArticleDetail(@PathVariable Integer id) {
        ArticleDetailVO result = articleService.getArticleDetail(id);
        return Message.success(result);
    }

    /**
     * 获取文章归档
     * @return 文章归档信息
     */
    @GetMapping("/archive")
    public Message<List<ArticleArchiveVO>> getArticleArchive() {
        List<ArticleArchiveVO> result = articleService.getArticleArchive();
        return Message.success(result);
    }

}
