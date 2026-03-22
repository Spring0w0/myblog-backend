package com.spring0w0.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.po.User;
import com.spring0w0.myblog.pojo.vo.UserProfileVO;

import java.util.Map;

/**
 * @author Spring0w0
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param loginInfo
     * @return 登录结果
     */
    Map<String, String> login(Map<String, String> loginInfo);

    /**
     * 获取用户信息
     * @return 用户信息
     */
    UserProfileVO getUserProfile();

    /**
     * 更新用户信息
     * @param userProfileVO 用户信息
     * @return 更新结果
     */
    UserProfileVO updateUserInfo(UserProfileVO userProfileVO);

}
