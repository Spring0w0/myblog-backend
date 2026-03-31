package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.mapper.TagMapper;
import com.spring0w0.myblog.pojo.po.ArticleTag;
import com.spring0w0.myblog.pojo.po.Tag;
import com.spring0w0.myblog.pojo.dto.TagDTO;
import com.spring0w0.myblog.pojo.vo.TagDetailVO;
import com.spring0w0.myblog.pojo.vo.TagPageVO;
import com.spring0w0.myblog.pojo.vo.TagVO;
import com.spring0w0.myblog.pojo.vo.UserTagPageVO;
import com.spring0w0.myblog.service.IArticleTagService;
import com.spring0w0.myblog.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    private final IArticleTagService articleTagService;

    /**
     * 保存标签
     * @param map 标签信息
     * @return 保存结果
     */
    @Override
    public boolean saveTag(Map<String, String> map) {
        if (MapUtil.isEmpty(map)) {
            throw new BusinessException("标签信息不能为空");
        }
        Tag tag = new Tag();
        tag.setName(map.get("name"));
        return this.save(tag);
    }

    /**
     * 删除标签
     * @param id 标签id
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeTagById(Integer id) {
        //  删除标签前，需要将该标签下的文章标签关系一并删除
        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, id));
        return this.removeById(id);
    }

    /**
     * 修改标签
     * @param id 标签id
     * @param tagDTO 标签信息
     * @return 修改结果
     */
    @Override
    public boolean updateTag(Integer id, TagDTO tagDTO
    ) {
        Tag tag = BeanUtil.copyProperties(tagDTO, Tag.class);
        if (ObjectUtil.isNull(id)) {
            throw new BusinessException("标签id不能为空");
        }
        return this.updateById(tag);
    }

    /**
     * 列出所有启用的标签
     * @return 标签列表
     */
    @Override
    public List<TagVO> listTag() {
        List<Tag> tagList = this.list(new LambdaQueryWrapper<Tag>().eq(Tag::getStatus, 1));
        if (ObjectUtil.isEmpty(tagList)) {
            throw new BusinessException("标签列表为空");
        }
        return BeanUtil.copyToList(tagList, TagVO.class);

    }

    /**
     * (管理端)分页列出所有标签
     * @param page 页码
     * @param pageSize 每页大小
      * @param name 标签名
      * @return 标签列表
     */
    @Override
    public TagPageVO listTagByPage(Integer page, Integer pageSize, String name) {
        Page<Tag> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .like(StrUtil.isNotBlank(name), Tag::getName, name)
                .orderByDesc(Tag::getCreateTime);
        Page<Tag> tagPage = this.page(pageParam, wrapper);
        TagPageVO tagPageVO = new TagPageVO();
        tagPageVO.setTotal((int) tagPage.getTotal());
        tagPageVO.setList(BeanUtil.copyToList(tagPage.getRecords(), TagDetailVO.class));
        return tagPageVO;
    }

    /**
     * (用户端)分页列出所有标签
     * @param page 页码
     * @param pageSize 每页大小
     * @return 标签列表
     */
    @Override
    public UserTagPageVO listTagByPage(Integer page, Integer pageSize) {
        Page<Tag> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .eq(Tag::getStatus, 1)
                .orderByDesc(Tag::getCreateTime);
        Page<Tag> tagPage = this.page(pageParam, wrapper);
        UserTagPageVO userTagPageVO = new UserTagPageVO();
        userTagPageVO.setTotal((int) tagPage.getTotal());
        userTagPageVO.setList(BeanUtil.copyToList(tagPage.getRecords(), TagVO.class));
        return userTagPageVO;
    }

    /**
     * 根据id获取标签
     * @param id 标签id
     * @return 标签信息
     */
    @Override
    public TagDetailVO getTagDetailById(Integer id) {
        Tag tag = this.getById(id);
        return BeanUtil.copyProperties(tag, TagDetailVO.class);
    }


}
