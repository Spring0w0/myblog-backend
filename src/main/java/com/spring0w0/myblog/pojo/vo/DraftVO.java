package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Spring0w0
 */
@Data
public class DraftVO {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
