package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.UploadVO;
import com.spring0w0.myblog.service.IUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传控制器
 * @author Spring0w0
 */
@RestController
@RequestMapping("admin/upload")
@RequiredArgsConstructor
public class UploadController {

    private final IUploadService uploadService;

    /**
     * 上传图片
     * @param file 图片文件
     * @param bizType 图片类型（cover: 封面, avatar: 头像, content: 文章插图）
     * @return 上传结果
     */
    @PostMapping
    public Message<UploadVO> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bizType") String bizType
    ) {
        return Message.success(uploadService.uploadImage(file, bizType), "上传成功");
    }
}
