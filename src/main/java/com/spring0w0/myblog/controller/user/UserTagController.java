package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.po.User;
import com.spring0w0.myblog.pojo.vo.TagPageVO;
import com.spring0w0.myblog.pojo.vo.UserTagPageVO;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class UserTagController {

    private final ITagService tagService;

    @GetMapping("/page")
    public Message<UserTagPageVO> getTagPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "16") Integer pageSize
    ) {
        UserTagPageVO vo = tagService.listTagByPage(page, pageSize);
        return Message.success(vo, "获取标签列表成功");
    }

}
