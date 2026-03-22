package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.dto.ArticleDTO;
import com.spring0w0.myblog.pojo.vo.ArticlePageVO;
import com.spring0w0.myblog.service.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("admin/article")
@RequiredArgsConstructor
public class ArticleController {
    private final IArticleService articleService;

    /**
     * 添加文章
     * @param articleDTO 文章信息
     * @return 添加结果
     */
    @PostMapping
    public Message<Object> addArticle(
            @RequestBody ArticleDTO articleDTO
            ) {
        return articleService.addArticle(articleDTO) ? Message.success("添加成功") : Message.error("添加失败");
    }

    /**
     * 修改文章
     * @param id 文章ID
     * @param articleDTO 文章信息
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Message<Object> updateArticle(
            @PathVariable Integer id,
            @RequestBody ArticleDTO articleDTO
            ) {
        return articleService.updateArticle(id, articleDTO) ? Message.success("修改成功") : Message.error("修改失败");
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Message<Object> deleteArticle(
            @PathVariable Integer id
            ) {
        return articleService.deleteArticle(id) ? Message.success("删除成功") : Message.error("删除失败");
    }

    /**
     * 根据ID获取文章
     * @param id 文章ID
     * @return 文章信息(由于该DTO的字段和接口文档要求的字段一致，所以复用)
     */
    @GetMapping("/{id}")
    public Message<ArticleDTO> getArticleById(@PathVariable Integer id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return Message.success(articleDTO);
    }

    /**
     * 获取文章分页
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param status 文章状态
     * @param title 文章标题
      * @return 文章分页信息
     */
    @GetMapping
    public Message<ArticlePageVO> getArticlePage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10")  Integer pageSize,
            @RequestParam(required = false)  Integer status,
            @RequestParam(required = false) String title
    ) {
        ArticlePageVO articlePageVO = articleService.getArticlePage(page, pageSize, status, title);
        return Message.success(articlePageVO);
    }
}
