package com.spring0w0.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.constants.ResponseCode;
import com.spring0w0.myblog.entity.Tags;
import com.spring0w0.myblog.service.ITagsService;
import com.spring0w0.myblog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagsService tagsService;

    /**
     * 获取所有标签及其计数
     * @return 标签列表
     */
    @GetMapping
    public Message<List<TagVO>> getAllTags() {
        List<TagVO> tags = tagsService.getAllTags();
        return Message.success(tags, "成功");
    }

    /**
     * 新增标签
     * @param map
     * @return
     */
    @PostMapping
    public Message<TagVO> createTag(@RequestBody Map<String,String> map) {
        TagVO tagVo = tagsService.saveTag(map.get("name"));
        return Message.success(tagVo);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Message<String> deleteTag(@PathVariable Long id) {
        boolean bool =  tagsService.deleteTag(id);
        if (bool) {
            return Message.success("");
        }else {
            return Message.error("");
        }
    }

}
