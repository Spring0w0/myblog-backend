package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.pojo.dto.WebConfigDTO;
import com.spring0w0.myblog.pojo.po.WebConfig;
import com.spring0w0.myblog.pojo.vo.UserWebConfigVO;
import com.spring0w0.myblog.pojo.vo.WebConfigVO;

import java.util.List;

/**
 * @author Spring0w0
 */
public interface IWebConfigService extends IService<WebConfig> {

    /**
     * (管理端)获取所有网站配置
     * @return 所有网站配置
     */
    List<WebConfigVO> getAllWebConfig();

    List<UserWebConfigVO> getAllUserWebConfig();


    /**
     * 批量更新网站配置
     * @param dtoList 配置项DTO列表
     * @return 是否更新成功
     */
    boolean updateBatch(List<WebConfigDTO> dtoList);
}
