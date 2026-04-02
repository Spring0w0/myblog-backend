package com.spring0w0.myblog.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.po.Article;
import com.spring0w0.myblog.pojo.po.Category;
import com.spring0w0.myblog.pojo.po.Tag;
import com.spring0w0.myblog.pojo.vo.StatsVO;
import com.spring0w0.myblog.service.IArticleService;
import com.spring0w0.myblog.service.ICategoryService;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class UserStatsController {

    private final IArticleService articleService;
    private final ITagService tagService;
    private final ICategoryService categoryService;

    @GetMapping
    public Message<StatsVO> getStats() {
        // 获取已发布的文章总数
        int articleTotal = (int) articleService.count(new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1));
        // 获取最新文章的更新时间
        LocalDateTime latestArticleTime = articleService.getOne(new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getUpdateTime)
                .last("limit 1"))
                .getUpdateTime();
        // 获取总阅读数
        int totalViewCount = articleService.list(new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1))
                .stream()
                .mapToInt(Article::getViewCount)
                .sum();


        // 获取标签总数
        int tagTotal = (int) tagService.count(new LambdaQueryWrapper<Tag>()
                .eq(Tag::getStatus, 1));
        // 获取分类总数
        int categoryTotal = (int) categoryService.count(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, 1));


        return Message.success(StatsVO.builder()
                .articleTotal(articleTotal)
                .tagTotal(tagTotal)
                .categoryTotal(categoryTotal)
                .totalViewCount(totalViewCount)
                .latestArticleTime(latestArticleTime)
                .build());
    }


}
