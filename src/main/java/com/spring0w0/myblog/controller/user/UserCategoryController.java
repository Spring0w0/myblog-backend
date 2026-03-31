package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.CategoryVO;
import com.spring0w0.myblog.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class UserCategoryController {

    private final ICategoryService categoryService;

    @GetMapping()
    public Message<List<CategoryVO>> listCategory() {
        return Message.success(categoryService.listCategory(), "获取分类列表成功");
    }


}
