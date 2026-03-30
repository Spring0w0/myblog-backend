package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.DraftVO;
import com.spring0w0.myblog.pojo.vo.HotArticleVO;
import com.spring0w0.myblog.pojo.vo.RecentArticleVO;
import com.spring0w0.myblog.service.IArticleService;
import com.spring0w0.myblog.service.ICategoryService;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final IArticleService articleService;
    private final ITagService tagService;
    private final ICategoryService categoryService;

    /**
     * 获取文章数量
     * @return 文章数量
     */
    @GetMapping("/article-num")
    public Message<Map<String, Long>> getArticleNum() {
        Long articleNum = articleService.count();
        Map<String, Long> map = new HashMap<>();
        map.put("articleNum", articleNum);
        return Message.success(map);
    }

    /**
     * 获取标签数量
     * @return 标签数量
     */
    @GetMapping("/tag-num")
    public Message<Map<String, Long>> getTagNum() {
        Long tagNum = tagService.count();
        Map<String, Long> map = new HashMap<>();
        map.put("tagNum", tagNum);
        return Message.success(map);
    }

    /**
     * 获取分类数量
     * @return 分类数量
     */
    @GetMapping("/category-num")
    public Message<Map<String, Long>> getCategoryNum() {
        Long categoryNum = categoryService.count();
        Map<String, Long> map = new HashMap<>();
        map.put("categoryNum", categoryNum);
        return Message.success(map);
    }

    /**
     * 获取热门文章
     * @return 热门文章
     */
    @GetMapping("/hot-article")
    public Message<List<HotArticleVO>> getHotArticle() {
        List<HotArticleVO> hotArticleVOList = articleService.getHotArticle();
        return Message.success(hotArticleVOList);
    }

    /**
     * 获取最近文章
     * @return 最近文章
     */
    @GetMapping("/recent-article")
    public Message<List<RecentArticleVO>> getRecentArticle() {
        List<RecentArticleVO> recentArticleVOList = articleService.getRecentArticle();
        return Message.success(recentArticleVOList);
    }

    @GetMapping("/draft")
    public Message<List<DraftVO>> getDraft() {
        List<DraftVO> draftVOList = articleService.getDraft();
        return Message.success(draftVOList);
    }
}
