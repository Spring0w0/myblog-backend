package com.spring0w0.myblog.controller;

import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.service.ITagsService;
import com.spring0w0.myblog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/api/tags")
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
}
