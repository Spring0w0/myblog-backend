package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.dto.TagDTO;
import com.spring0w0.myblog.pojo.vo.TagDetailVO;
import com.spring0w0.myblog.pojo.vo.TagPageVO;
import com.spring0w0.myblog.pojo.vo.TagVO;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/admin/tag")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    /**
     * 新增标签
     * @param tag 标签信息
     * @return 保存结果
     */
    @PostMapping()
    public Message<Object> saveTag(
            @RequestBody Map<String, String> tag
    ) {
        boolean result = tagService.saveTag(tag);
        if (!result){
            return Message.error("保存失败");
        }else {
            return Message.success("保存成功");
        }
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Message<Object> deleteTag(
            @PathVariable Integer id
    ) {
        boolean result = tagService.removeTagById(id);
        if (!result){
            return Message.error("删除失败");
        }else {
            return Message.success("删除成功");
        }
    }

    /**
     * 修改标签
     * @param id 标签ID
     * @param tag 标签信息
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Message<Object> updateTag(
            @PathVariable Integer id,
            @RequestBody TagDTO tag
    ) {
        boolean result = tagService.updateTag(id, tag);
        if (!result){
            return Message.error("修改失败");
        }else {
            return Message.success("修改成功");
        }
    }

    /**
     * 列出所有启用的标签
     * @return  标签列表
     */
    @GetMapping("/all")
    public Message<List<TagVO>> listTag() {
        return Message.success(tagService.listTag());
    }

    /**
     * 分页列出所有标签
     * @param page 页码
     * @param pageSize 每页大小
     * @param name 标签名
     * @return 标签列表
     */
    @GetMapping
    public Message<TagPageVO> listTagByPage(
            @RequestParam(defaultValue = "1")  Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name
    ) {
        return Message.success(tagService.listTagByPage(page, pageSize, name));
    }

    @GetMapping("/{id}")
    public Message<TagDetailVO> getTagById(
            @PathVariable Integer id
    ) {
        return Message.success(tagService.getTagDetailById(id));
    }
}
