package com.spring0w0.myblog.controller;

import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.service.ICategoriesService;
import com.spring0w0.myblog.vo.CategoriesVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final ICategoriesService categoriesService;

    /**
     * 获取所有分类及其计数
     * @return 分类列表
     */
    @GetMapping
    public Message<List<CategoriesVO>> getAllCategories() {
        List<CategoriesVO> categories = categoriesService.getAllCategories();
        return Message.success(categories, "成功");
    }
}
