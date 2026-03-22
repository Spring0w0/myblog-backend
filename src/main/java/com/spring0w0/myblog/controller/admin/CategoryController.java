package com.spring0w0.myblog.controller.admin;


import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.dto.CategoryDTO;
import com.spring0w0.myblog.pojo.vo.CategoryDetailVO;
import com.spring0w0.myblog.pojo.vo.CategoryPageVO;
import com.spring0w0.myblog.pojo.vo.CategoryVO;
import com.spring0w0.myblog.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * 新增分类
     * @param map 分类信息
     * @return 新增结果
     */
    @PostMapping
    public Message<Object> addCategory(
            @RequestBody Map<String, String> map
            ) {
        boolean result = categoryService.addCategory(map);
        if (!result){
            return Message.error("保存失败");
        }else {
            return Message.success("保存成功");
        }
    }

    /**
     * 修改分类
     * @param id 分类ID
     * @param categoryDTO 分类信息
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Message<Object> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryDTO categoryDTO
            ) {
        boolean result = categoryService.updateCategory(id ,categoryDTO);
        if (!result){
            return Message.error("修改失败");
        }else {
            return Message.success("修改成功");
        }
    }

    /**
     * 删除分类
     * @param id 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Message<Object> deleteCategory(@PathVariable Integer id) {
        boolean result = categoryService.deleteCategory(id);
        if (!result){
            return Message.error("删除失败");
        }else {
            return Message.success("删除成功");
        }
    }

    /**
     * 获取所有启用的分类
     * @return 所有启用的分类
     */
    @GetMapping("/all")
    public Message<List<CategoryVO>> getAllCategory() {
        return Message.success(categoryService.listCategory());
    }

    /**
     * 分页获取分类
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 分类名
     * @return 分类列表
     */
    @GetMapping
    public Message<CategoryPageVO> listCategoryByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name
    ) {
        return Message.success(categoryService.listCategoryByPage(page, pageSize, name));
    }

    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public Message<CategoryDetailVO> getCategoryDetailById(
            @PathVariable Integer id
    ) {
        return Message.success(categoryService.getCategoryDetailById(id));
    }

}
