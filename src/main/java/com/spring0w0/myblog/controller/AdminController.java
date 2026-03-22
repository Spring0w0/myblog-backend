package com.spring0w0.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.spring0w0.myblog.common.Message;
import com.spring0w0.myblog.dto.PostDTO;
import com.spring0w0.myblog.entity.Posts;
import com.spring0w0.myblog.service.IPostsService;
import com.spring0w0.myblog.vo.PostEditDetailVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    //从application.yaml中读取管理员密码
    @Value("${admin.password}")
    private String adminPassword;

    private final IPostsService postsService;

    /**
     * 管理员登录
     * @param loginData 登录数据
     * @return 登录结果
     */
    @PostMapping("/login")
    public Message<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String password = loginData.get("password");
        
        logger.info("管理员登录尝试");
        
        // 简单验证密码
        if (adminPassword.equals(password)) {
            logger.info("管理员登录成功");
            Map<String, String> data = new HashMap<>();
            data.put("token", "authenticated");
            return Message.success(data, "登录成功");
        } else {
            logger.warn("管理员登录失败：密码错误");
            return Message.error(401, "密码错误");
        }
    }

    /**
     * 创建文章
     * @param postDTO 文章数据
     * @return 创建结果
     */
    @PostMapping("/posts")
    public Message<PostEditDetailVO> createPost(@RequestBody PostDTO postDTO) {
        logger.info("创建文章");
        try {
            Posts posts = BeanUtil.copyProperties(postDTO, Posts.class);
            postsService.save(posts);
            PostEditDetailVO postEditDetailVO = BeanUtil.copyProperties(posts, PostEditDetailVO.class);
            return Message.success(postEditDetailVO, "创建成功");
        } catch (Exception e) {
            logger.error("创建文章失败", e);
            return Message.error(500, "创建失败");
        }
    }

    /**
     * 获取文章详情（用于编辑）
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/posts/{id}")
    public Message<PostEditDetailVO> getPostById(@PathVariable long id) {
        logger.info("获取文章详情：{}", id);
        try {
            Posts posts = postsService.getById(id);
            PostEditDetailVO postEditDetailVO = BeanUtil.copyProperties(posts, PostEditDetailVO.class);
            return Message.success(postEditDetailVO, "获取成功");
        } catch (Exception e) {
            logger.error("获取文章详情失败", e);
            return Message.error(500, "获取失败");
        }
    }

    /**
     * 更新文章
     * @param id 文章ID
     * @param postDTO 文章数据
     * @return 更新结果
     */
    @PutMapping("/posts/{id}")
    public Message<PostEditDetailVO> updatePost(@PathVariable long id, @RequestBody PostDTO postDTO) {
        logger.info("更新文章：{}", id);
        try {
            Posts posts = BeanUtil.copyProperties(postDTO, Posts.class);
            postsService.updateById(posts);
            PostEditDetailVO postEditDetailVO = BeanUtil.copyProperties(posts, PostEditDetailVO.class);
            return Message.success(postEditDetailVO, "更新成功");
        } catch (Exception e) {
            logger.error("更新文章失败", e);
            return Message.error(500, "更新失败");
        }
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    @DeleteMapping("/posts/{id}")
    public Message<String> deletePost(@PathVariable long id) {
        logger.info("删除文章：{}", id);
        try {
            postsService.removeById(id);
            return Message.success("", "删除成功");
        } catch (Exception e) {
            logger.error("删除文章失败", e);
            return Message.error(500, "删除失败");
        }
    }
}
