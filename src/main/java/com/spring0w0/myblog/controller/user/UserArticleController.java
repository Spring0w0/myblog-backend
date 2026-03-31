package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.ArticleCardListVO;
import com.spring0w0.myblog.service.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class UserArticleController {

    private final IArticleService articleService;

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


}
