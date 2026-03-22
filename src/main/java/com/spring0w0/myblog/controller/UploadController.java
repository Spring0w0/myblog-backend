package com.spring0w0.myblog.controller;

import cn.hutool.core.lang.UUID;
import com.spring0w0.myblog.common.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/")
@CrossOrigin // 开发阶段允许跨域
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    // 允许的图片类型
    private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of(
            ".jpg", ".jpeg", ".png", ".gif", ".webp"
    );

    // 最大文件大小：10MB
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @PostMapping("/upload")
    public Message<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "article") String type) {
        
        if (file.isEmpty()) {
            return Message.error("文件不能为空");
        }

        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return Message.error("文件大小不能超过10MB");
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Message.error("文件名不能为空");
        }
        
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!ALLOWED_IMAGE_TYPES.contains(suffix)) {
            return Message.error("只允许上传jpg、jpeg、png、gif、webp格式的图片");
        }

        try {
            // 1. 根据类型决定子目录：covers/user/ 或 articles/
            String subDir = type.equals("cover") ? "covers/user/" : "articles/";
            
            // 2. 获取当前日期
            String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/"));
            
            // 3. 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + suffix;

            // 4. 拼接物理路径并创建目录
            File targetDir = new File(uploadPath + subDir + dateDir);
            if (!targetDir.exists()) {
                boolean created = targetDir.mkdirs();
                if (!created) {
                    return Message.error("创建目录失败");
                }
            }

            // 5. 保存文件
            File dest = new File(targetDir, fileName);
            file.transferTo(dest);

            // 6. 构造返回的虚拟路径 (用于前端访问)
            String virtualPath = "/uploads/" + subDir + dateDir + fileName;
            
            Map<String, String> result = new HashMap<>();
            result.put("url", virtualPath);
            return Message.success(result, "上传成功");

        } catch (IOException e) {
            e.printStackTrace();
            return Message.error("文件上传失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("上传过程中出现错误：" + e.getMessage());
        }
    }
}