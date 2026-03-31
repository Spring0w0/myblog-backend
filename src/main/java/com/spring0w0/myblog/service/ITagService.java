package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.pojo.po.Tag;
import com.spring0w0.myblog.pojo.dto.TagDTO;
import com.spring0w0.myblog.pojo.vo.TagDetailVO;
import com.spring0w0.myblog.pojo.vo.TagPageVO;
import com.spring0w0.myblog.pojo.vo.TagVO;
import com.spring0w0.myblog.pojo.vo.UserTagPageVO;

import java.util.List;
import java.util.Map;

/**
 * @author Spring0w0
 */
public interface ITagService extends IService<Tag> {
    /**
     * 保存标签
     * @param map 标签信息
     * @return 保存结果
     */
    boolean saveTag(Map<String, String> map);

    /**
     * 根据id删除标签
     * @param id 标签id
     * @return 删除结果
     */
    boolean removeTagById(Integer id);

    /**
     * 根据id更新标签
     * @param id  标签id
     * @param tag 标签信息
     * @return 更新结果
     */
    boolean updateTag(Integer id, TagDTO tag);

    /**
     * 列出所有启用的标签
     * @return 标签列表
     */
    List<TagVO> listTag();

    /**
     * (管理端)分页列出所有标签
     * @param page 页码
     * @param pageSize 每页大小
     * @return 标签列表
     */
    TagPageVO listTagByPage(Integer page, Integer pageSize, String name);

    /**
     * (用户端)分页列出所有标签·
     * @param page 页码
     * @param pageSize 每页大小
     * @return 标签列表
     */
    UserTagPageVO listTagByPage(Integer page, Integer pageSize);

    /**
     * 根据id获取标签
     * @param id 标签id
     * @return 标签信息
     */
    TagDetailVO getTagDetailById(Integer id);
}
