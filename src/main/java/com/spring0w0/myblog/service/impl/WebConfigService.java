package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.mapper.WebConfigMapper;
import com.spring0w0.myblog.pojo.dto.WebConfigDTO;
import com.spring0w0.myblog.pojo.po.WebConfig;
import com.spring0w0.myblog.pojo.vo.WebConfigVO;
import com.spring0w0.myblog.service.IWebConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Service
public class WebConfigService extends ServiceImpl<WebConfigMapper, WebConfig> implements IWebConfigService {

    /**
     * 获取所有网站配置
     * @return 所有网站配置
     */
    @Override
    public List<WebConfigVO> getAllWebConfig() {
        // 1.查询数据库原始数据
        List<WebConfig> webConfigs = this.list();

        // 2.转换为VO列表
        return webConfigs.stream().map(config -> {
            WebConfigVO vo = BeanUtil.copyProperties(config, WebConfigVO.class);

            String val = config.getConfigValue();

            // 特殊处理
            if (JSONUtil.isTypeJSON(val)) {
                // 如果是json字符串，解析成JSONArray或JSONObject
                vo.setConfigValue(JSONUtil.parse(val));
            }else {
                // 保持原样
                vo.setConfigValue(val);
            }
            return vo;
        }).toList();

    }

    /**
     * 批量更新网站配置
     * @param dtoList 配置项DTO列表
     * @return 是否更新成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatch(List<WebConfigDTO> dtoList) {
        if (CollUtil.isEmpty(dtoList)) {
            throw new BusinessException("配置项为空");
        }

        List<WebConfig> webConfigs = dtoList.stream().map(dto -> {
            WebConfig entity = new WebConfig();
            entity.setId(dto.getId());
            entity.setConfigKey(dto.getConfigKey());

            Object val = dto.getConfigValue();
            // 判断是否需要转 JSON 字符串
            if (val instanceof Iterable || val instanceof Map || val.getClass().isArray()) {
                // 转 JSON 字符串
                entity.setConfigValue(JSONUtil.toJsonStr(val));
            } else {
                entity.setConfigValue(Convert.toStr(val));
            }

            return entity;
        }).toList();

        return this.updateBatchById(webConfigs);
    }
}
