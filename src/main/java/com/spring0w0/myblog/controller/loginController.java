package com.spring0w0.myblog.controller;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.service.IUserService;
import com.spring0w0.myblog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class loginController {

    private static final Logger logger = LoggerFactory.getLogger(loginController.class);

    private final IUserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public Message<Map<String, String>> login(@RequestBody Map<String, String> loginInfo) {
        logger.info("{}:登录尝试", loginInfo.get("username"));
        Map<String, String> result = userService.login(loginInfo);
        return Message.success(result, "登录成功");
    }

}
