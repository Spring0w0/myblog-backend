package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.UserProfileVO;
import com.spring0w0.myblog.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final IUserService userService;

    @GetMapping
    public Message<UserProfileVO> getUserProfile() {
        return Message.success(userService.getUserProfile(), "获取用户信息成功");
    }

}
