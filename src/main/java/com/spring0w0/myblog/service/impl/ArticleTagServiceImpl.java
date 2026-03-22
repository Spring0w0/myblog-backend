package com.spring0w0.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.mapper.ArticleTagMapper;
import com.spring0w0.myblog.pojo.po.ArticleTag;
import com.spring0w0.myblog.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author Spring0w0
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {
}
