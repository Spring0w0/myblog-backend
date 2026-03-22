package com.spring0w0.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.common.exception.BusinessException;
import com.spring0w0.myblog.mapper.UserMapper;
import com.spring0w0.myblog.pojo.po.User;
import com.spring0w0.myblog.pojo.vo.UserProfileVO;
import com.spring0w0.myblog.service.IUserService;
import com.spring0w0.myblog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring0w0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final JwtUtil jwtUtil;

    /**
     * 用户登录验证
     * @param loginInfo 登录信息
     * @return 登录结果，包含 token
     */
    @Override
    public Map<String, String> login(Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");

        // 参数校验
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名或密码不能为空");
        }

        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = getOne(queryWrapper);

        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码是否正确
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 生成 JWT token
        String token = jwtUtil.generateToken(user.getId().toString());

        // 返回登录结果
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return result;
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    public UserProfileVO getUserProfile() {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getId, 1));
        return BeanUtil.copyProperties(user, UserProfileVO.class);
    }

    @Override
    public UserProfileVO updateUserInfo(UserProfileVO userProfileVO) {
        User user = new User();
        BeanUtil.copyProperties(userProfileVO, user);
        // 数据库里的user表只有一条数据(站主信息)，id固定为1
        user.setId(1);
        updateById(user);
        return BeanUtil.copyProperties(user, UserProfileVO.class);
    }
}
