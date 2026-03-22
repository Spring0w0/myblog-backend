package com.spring0w0.myblog.controller;

import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.service.ICategoriesService;
import com.spring0w0.myblog.vo.CategoriesVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/categories")
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

    @PostMapping
    public Message<CategoriesVO> saveCategories(@RequestBody Map<String, String>  params) {
        CategoriesVO categoriesVO = categoriesService.saveCategories(params.get("name"));
        return Message.success(categoriesVO);
    }

    @DeleteMapping("{id}")
    public Message<String> deleteCategories(@PathVariable Long id) {
        boolean bool =  categoriesService.deleteTag(id);
        if (bool) {
            return Message.success("");
        }else {
            return Message.error("");
        }
    }

}
