package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.entity.Categories;
import com.spring0w0.myblog.vo.CategoriesVO;

import java.util.List;

/**
 * @author Spring0w0
 */
public interface ICategoriesService extends IService<Categories> {

    /**
     * 获取所有分类及其计数
     * @return 分类列表
     */
    List<CategoriesVO> getAllCategories();
}
