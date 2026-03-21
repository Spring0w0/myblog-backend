package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.entity.Tags;
import com.spring0w0.myblog.mapper.TagsMapper;
import com.spring0w0.myblog.service.ITagsService;
import com.spring0w0.myblog.vo.TagVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring0w0
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

    @Override
    public List<TagVO> getAllTags() {
        List<Tags> tags = this.lambdaQuery()
                .orderByDesc(Tags::getCount)
                .list();
        
        return BeanUtil.copyToList(tags, TagVO.class);
    }
}
