package com.spring0w0.myblog.service;

import com.spring0w0.myblog.pojo.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传服务接口
 * @author Spring0w0
 */
public interface IUploadService {

    /**
     * 上传图片
     * @param file 图片文件
     * @param bizType 图片类型（cover: 封面, avatar: 头像, content: 文章插图）
     * @return 上传结果
     */
    UploadVO uploadImage(MultipartFile file, String bizType);
}
