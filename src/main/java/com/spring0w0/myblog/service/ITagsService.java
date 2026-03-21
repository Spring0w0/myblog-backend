package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.entity.Tags;
import com.spring0w0.myblog.vo.TagVO;

import java.util.List;

/**
 * @author Spring0w0
 */
public interface ITagsService extends IService<Tags> {

    /**
     * 获取所有标签及其计数
     * @return 标签列表
     */
    List<TagVO> getAllTags();
}
