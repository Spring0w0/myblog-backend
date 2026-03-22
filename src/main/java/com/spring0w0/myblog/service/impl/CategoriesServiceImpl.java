package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.entity.Categories;
import com.spring0w0.myblog.mapper.CategoriesMapper;
import com.spring0w0.myblog.service.ICategoriesService;
import com.spring0w0.myblog.vo.CategoriesVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring0w0
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements ICategoriesService {

    @Override
    public List<CategoriesVO> getAllCategories() {
        List<Categories> categories = this.lambdaQuery()
                .orderByDesc(Categories::getCount)
                .list();
        
        return BeanUtil.copyToList(categories, CategoriesVO.class);
    }

    @Override
    public CategoriesVO saveCategories(String name) {
        Categories categories = Categories.builder()
                .name(name)
                .count(0)
                .build();
        return BeanUtil.copyProperties(categories, CategoriesVO.class);
    }

    @Override
    public boolean deleteTag(Long id) {
        return this.removeById(id);
    }
}
