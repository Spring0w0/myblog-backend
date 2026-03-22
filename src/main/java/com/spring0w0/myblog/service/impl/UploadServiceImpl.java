package com.spring0w0.myblog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.pojo.vo.UploadVO;
import com.spring0w0.myblog.service.IUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * 上传服务实现类
 * @author Spring0w0
 */
@Service
public class UploadServiceImpl implements IUploadService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.base-url}")
    private String baseUrl;

    /**
     * 允许的图片类型
     */
    private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

    /**
     * 允许的业务类型
     */
    private static final Set<String> ALLOWED_BIZ_TYPES = Set.of("cover", "avatar", "content");

    @Override
    public UploadVO uploadImage(MultipartFile file, String bizType) {
        // 校验文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException("请选择要上传的文件");
        }

        // 校验业务类型
        if (!ALLOWED_BIZ_TYPES.contains(bizType)) {
            throw new BusinessException("不支持的图片类型，仅支持：cover, avatar, content");
        }

        // 校验文件类型
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new BusinessException("不支持的文件格式，仅支持：jpg, png, gif, webp");
        }

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null ? FileUtil.getSuffix(originalFilename) : "jpg";

        // 生成唯一文件名
        String newFileName = IdUtil.fastSimpleUUID() + "." + suffix;

        // 创建目标目录（按业务类型分目录存储）
        String targetDir = uploadPath + bizType + "/";
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存文件
        File destFile = new File(targetDir + newFileName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }

        // 构建响应
        return UploadVO.builder()
                .url(baseUrl + "/" + bizType + "/" + newFileName)
                .bizType(bizType)
                .size(file.getSize())
                .build();
    }
}
