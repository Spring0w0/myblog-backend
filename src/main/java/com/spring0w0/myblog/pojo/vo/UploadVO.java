package com.spring0w0.myblog.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传响应VO
 * @author Spring0w0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadVO {
    /**
     * 图片URL
     */
    private String url;

    /**
     * 图片类型（cover: 封面, avatar: 头像, content: 文章插图）
     */
    private String bizType;

    /**
     * 图片大小（单位：字节）
     */
    private Long size;
}
