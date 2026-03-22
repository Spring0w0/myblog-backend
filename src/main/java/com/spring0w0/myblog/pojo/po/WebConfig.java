package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring0w0
 */
@TableName("web_config")
@EqualsAndHashCode(callSuper = true)
@Data
public class WebConfig extends BaseEntity {

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 说明
     */
    private String description;

}
