package com.huzi.example.common.service;

import com.huzi.example.common.model.User;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     */
    default Integer getNumber() {
        return 1;
    }

}
