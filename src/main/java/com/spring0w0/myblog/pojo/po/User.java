package com.spring0w0.myblog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring0w0.myblog.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring0w0
 */
@TableName("user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 用户名/账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称(默认是user)
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;





}
