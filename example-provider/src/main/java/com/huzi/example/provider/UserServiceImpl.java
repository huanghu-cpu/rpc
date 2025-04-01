package com.huzi.example.provider;
import com.huzi.example.common.model.User;
import com.huzi.example.common.service.UserService;


public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }

}
