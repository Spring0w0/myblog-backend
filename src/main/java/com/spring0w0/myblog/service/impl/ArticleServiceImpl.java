package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.mapper.ArticleMapper;
import com.spring0w0.myblog.pojo.dto.ArticleDTO;
import com.spring0w0.myblog.pojo.po.Article;
import com.spring0w0.myblog.pojo.po.ArticleTag;
import com.spring0w0.myblog.pojo.po.Category;
import com.spring0w0.myblog.pojo.po.Tag;
import com.spring0w0.myblog.pojo.vo.*;
import com.spring0w0.myblog.service.IArticleService;
import com.spring0w0.myblog.service.IArticleTagService;
import com.spring0w0.myblog.service.ICategoryService;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Value("${upload.base-url}")
    private String baseUrl;

    private final IArticleTagService articleTagService;
    private final ICategoryService categoryService;
    private final ITagService tagService;

    /**
     * 添加文章
     * @param articleDTO 文章信息
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addArticle(ArticleDTO articleDTO) {
        if (ObjectUtil.isEmpty(articleDTO)) {
            throw new BusinessException("文章信息不能为空");
        }
        Article article = BeanUtil.copyProperties(articleDTO, Article.class);
        // 如果上传的文章没有设置封面，则选用一个随机封面
        if (ObjectUtil.isEmpty(article.getCover())) {
            article.setCover(baseUrl + "/cover/default/" + (int) (Math.random() * 6 + 1) + ".webp");
        }
        // 保存文章
        this.save(article);

        //保存文章与标签的关联关系
        List<Integer> tagIds = articleDTO.getTagIds();
        List<ArticleTag> articleTags = tagIds.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        // 批量保存
        return articleTagService.saveBatch(articleTags);
    }

    /**
     * 修改文章
     * @param id 文章ID
     * @param articleDTO 文章信息
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateArticle(Integer id, ArticleDTO articleDTO) {
        if (ObjectUtil.isEmpty(articleDTO)) {
            throw new BusinessException("文章信息不能为空");
        }
        Article article = BeanUtil.copyProperties(articleDTO, Article.class);
        // 如果文章没有设置封面，则选用一个随机封面
        if (ObjectUtil.isEmpty(article.getCover())) {
            article.setCover(baseUrl + "/cover/default/" + (int) (Math.random() * 6 + 1) + ".webp");
        }
        // 更新文章
        this.updateById(article);

            // 清理旧的关联关系
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getArticleId, id);
            articleTagService.remove(queryWrapper);

        // 保存新的关联关系
        List<Integer> tagIds = articleDTO.getTagIds();
        List<ArticleTag> articleTags = tagIds.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        // 批量保存
        return articleTagService.saveBatch(articleTags);

    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    @Override
    public boolean deleteArticle(Integer id) {
        // 先删除文章与标签的关联关系
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagService.remove(queryWrapper);

        // 删除文章
        return this.removeById(id);
    }

    /**
     * 根据ID获取文章
     * @param id 文章ID
      * @return 文章信息
     */
    @Override
    public ArticleDTO getArticleById(Integer id) {
        // 先查询文章
        Article article = this.getById(id);
        if (ObjectUtil.isEmpty(article)) {
            throw new BusinessException("文章不存在");
        }
        ArticleDTO articleDTO = BeanUtil.copyProperties(article, ArticleDTO.class);

        // 查询文章与标签的关联关系
        List<ArticleTag> articleTags = articleTagService.list(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, id));
        articleDTO.setTagIds(articleTags.stream().map(ArticleTag::getTagId).toList());
        return articleDTO;

    }

    @Override
    public ArticlePageVO getArticlePage(Integer page, Integer pageSize, Integer status, String title) {
        // 分页获取文章基础信息
        Page<Article> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .like(StrUtil.isNotBlank(title), Article::getTitle, title)
                .eq(ObjectUtil.isNotEmpty(status), Article::getStatus, status)
                .orderByDesc(Article::getCreateTime);

        Page<Article> articlePage = this.page(pageParam, queryWrapper);
        List<Article> articles = articlePage.getRecords();

        if (ObjectUtil.isEmpty(articles)) {
            throw new BusinessException("没有找到文章");
        }

        // 获取所有文章ID和分类ID
        List<Integer> articleIds = articles.stream().map(Article::getId).toList();
        List<Integer> categoryIds = articles.stream().map(Article::getCategoryId).distinct().toList();

        // 批量查询分类信息，构建Map
        Map<Integer, Category> categoryMap = categoryService.listByIds(categoryIds).stream()
                .collect(java.util.stream.Collectors.toMap(Category::getId, c -> c));

        // 批量查询文章-标签关联关系
        List<ArticleTag> articleTags = articleTagService.list(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIds));

        // 获取所有标签ID并批量查询标签信息
        List<Integer> tagIds = articleTags.stream().map(ArticleTag::getTagId).distinct().toList();
        Map<Integer, Tag> tagMap = tagIds.isEmpty() ? java.util.Collections.emptyMap() :
                tagService.listByIds(tagIds).stream()
                        .collect(java.util.stream.Collectors.toMap(Tag::getId, t -> t));

        // 构建文章ID到标签列表的映射
        Map<Integer, List<TagVO>> articleTagMap = articleTags.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        ArticleTag::getArticleId,
                        java.util.stream.Collectors.mapping(
                                at -> {
                                    Tag tag = tagMap.get(at.getTagId());
                                    if (tag != null) {
                                        TagVO tagVO = new TagVO();
                                        tagVO.setId(tag.getId());
                                        tagVO.setName(tag.getName());
                                        return tagVO;
                                    }
                                    return null;
                                },
                                java.util.stream.Collectors.filtering(t -> t != null, java.util.stream.Collectors.toList())
                        )
                ));

        // 转换为ArticleVO列表
        List<ArticleVO> articleVOList = articles.stream()
                .map(article -> {
                    ArticleVO articleVO = BeanUtil.copyProperties(article, ArticleVO.class);
                    // 设置分类信息
                    Category category = categoryMap.get(article.getCategoryId());
                    if (category != null) {
                        CategoryVO categoryVO = new CategoryVO();
                        categoryVO.setId(category.getId());
                        categoryVO.setName(category.getName());
                        articleVO.setCategory(categoryVO);
                    }
                    // 设置标签列表
                    articleVO.setTag(articleTagMap.getOrDefault(article.getId(), java.util.Collections.emptyList()));
                    return articleVO;
                })
                .toList();

        // 构建并返回分页结果
        ArticlePageVO result = new ArticlePageVO();
        result.setTotal((int) articlePage.getTotal());
        result.setList(articleVOList);
        return result;
    }

    @Override
    public List<HotArticleVO> getHotArticle() {
        return  this.list(new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getViewCount)
                .last("limit 5"))
                .stream()
                .map(article -> {
                    return BeanUtil.copyProperties(article, HotArticleVO.class);
                })
                .toList();
    }

    @Override
    public List<RecentArticleVO> getRecentArticle() {
        return this.list(new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getCreateTime)
                .last("limit 5"))
                .stream()
                .map(article -> {
                    return BeanUtil.copyProperties(article, RecentArticleVO.class);
                })
                .toList();
    }

    @Override
    public List<DraftVO> getDraft() {
        return this.list(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, 0)
                .orderByDesc(Article::getUpdateTime)
                .last("limit 5"))
                .stream()
                .map(article -> {
                    return BeanUtil.copyProperties(article, DraftVO.class);
                })
                .toList();
    }
}
