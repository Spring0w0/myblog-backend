package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.mapper.CategoryMapper;
import com.spring0w0.myblog.pojo.dto.CategoryDTO;
import com.spring0w0.myblog.pojo.po.Article;
import com.spring0w0.myblog.pojo.po.Category;
import com.spring0w0.myblog.pojo.vo.CategoryDetailVO;
import com.spring0w0.myblog.pojo.vo.CategoryPageVO;
import com.spring0w0.myblog.pojo.vo.CategoryVO;
import com.spring0w0.myblog.service.IArticleService;
import com.spring0w0.myblog.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final IArticleService articleService;


    /**
     * 新增分类
     * @param map 分类信息
     * @return 新增结果
     */
    @Override
    public boolean addCategory(Map<String, String> map) {
        if (MapUtil.isEmpty(map)) {
            throw new BusinessException("分类名称不能为空");
        }
        String categoryName = map.get("name");
        Category category = new Category();
        category.setName(categoryName);
        return this.save(category);
    }

    /**
     * 修改分类
     * @param id 分类ID
     * @param categoryDTO 分类信息
     * @return 修改结果
     */
    @Override
    public boolean updateCategory(Integer id, CategoryDTO categoryDTO) {
        if (ObjectUtil.isNull(categoryDTO)) {
            throw new BusinessException("分类信息不能为空");
        }
        Category category = BeanUtil.copyProperties(categoryDTO, Category.class);
        return this.updateById(category);
    }

    @Override
    public boolean deleteCategory(Integer id) {
        // 1. 不能删除“未分类”本身
        if (ObjectUtil.equal(id, 1)) {
            throw new BusinessException("默认分类不能删除");
        }
        // 删除分类前，需要将该分类下的文章全部设置成未分类(category_id = 1)
        articleService.update(new LambdaUpdateWrapper<Article>()
                .eq(Article::getCategoryId, id)
                .set(Article::getCategoryId, 1));
        // 再删除该分类
        return this.removeById(id);
    }

    /**
     * 获取所有启用的分类
     * @return 所有启用的分类
     */
    @Override
    public List<CategoryVO> listCategory() {
        List<Category> categoryList = this.list(new LambdaQueryWrapper<Category>().eq(Category::getStatus, 1));
        if (ObjectUtil.isEmpty(categoryList)) {
            throw new BusinessException("分类列表为空");
        }
        return BeanUtil.copyToList(categoryList, CategoryVO.class);
    }

    /**
     * 分页获取分类
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 分类名
     * @return 分类列表
     */
    @Override
    public CategoryPageVO listCategoryByPage(Integer page, Integer pageSize, String name) {
        Page<Category> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>()
                .like(StrUtil.isNotBlank(name), Category::getName, name)
                .orderByDesc(Category::getCreateTime);
        Page<Category> categoryPage = this.page(pageParam, wrapper);
        CategoryPageVO categoryPageVO = new CategoryPageVO();
        categoryPageVO.setTotal((int) categoryPage.getTotal());
        categoryPageVO.setList(BeanUtil.copyToList(categoryPage.getRecords(), CategoryDetailVO.class));
        return categoryPageVO;
    }

    /**
     * 根据ID获取分类详情
     * @param id 分类ID
      * @return 分类详情
     */
    @Override
    public CategoryDetailVO getCategoryDetailById(Integer id) {
        Category category = this.getById(id);
        return BeanUtil.copyProperties(category, CategoryDetailVO.class);
    }
}
