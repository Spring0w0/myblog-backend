package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.UserProfileVO;
import com.spring0w0.myblog.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("admin/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/profile")
    public Message<UserProfileVO> getUserProfile() {
        return Message.success(userService.getUserProfile(), "获取用户信息成功");
    }

    /**
     * 更新用户信息
     * @param userProfileVO 用户信息(这个VO和要用到的DTO字段一样，所以复用了)
     * @return 更新结果
     */
    @PutMapping("/profile")
    public Message<UserProfileVO> updateUserInfo(
            @RequestBody UserProfileVO userProfileVO
    ) {
        return Message.success(userService.updateUserInfo(userProfileVO), "更新用户信息成功");
    }

}
