package com.huzi.example.consumer;

import com.huzi.example.common.model.User;
import com.huzi.example.common.service.UserService;
import com.huzi.hurpc.proxy.ServiceProxyFactory;



/**
 * 简易服务消费者示例
 *
 * @author
 * @learn
 * @from
 */
public class ConsumerExample {


    public static void main(String[] args) {
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("huzi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        long number = userService.getNumber();
        System.out.println(number);

    }
}


