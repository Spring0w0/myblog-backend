package com.spring0w0.myblog.pojo.vo;

import lombok.Data;

/**
 * @author Spring0w0
 */
@Data
public class UserWebConfigVO {

    /**
     * 配置项键
     */
    private String configKey;

    /**
     * 配置项值 这里改为 Object，支持 String 或 JSON 转换后的 List/Map
     */
    private Object configValue;

}
