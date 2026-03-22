package com.spring0w0.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring0w0.myblog.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Spring0w0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
