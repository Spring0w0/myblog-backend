package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.pojo.dto.CategoryDTO;
import com.spring0w0.myblog.pojo.po.Category;
import com.spring0w0.myblog.pojo.vo.CategoryDetailVO;
import com.spring0w0.myblog.pojo.vo.CategoryPageVO;
import com.spring0w0.myblog.pojo.vo.CategoryVO;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 新增分类
     * @param map 分类信息
     * @return 新增结果
     */
    boolean addCategory(Map<String, String> map);

    /**
     * 修改分类
     * @param id 分类ID
     * @param categoryDTO 分类信息
     * @return 修改结果
     */
    boolean updateCategory(Integer id, CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id 分类ID
     * @return 删除结果
     */
    boolean deleteCategory(Integer id);

    /**
     * 获取所有启用的分类
     * @return 所有启用的分类
     */
    List<CategoryVO> listCategory();

    /**
     * 分页获取分类
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 分类名
     * @return 分类列表
     */
    CategoryPageVO listCategoryByPage(Integer page, Integer pageSize, String name);

    /**
     * 根据ID获取分类详情
     * @param id 分类ID
     * @return 分类详情
     */
    CategoryDetailVO getCategoryDetailById(Integer id);
}
