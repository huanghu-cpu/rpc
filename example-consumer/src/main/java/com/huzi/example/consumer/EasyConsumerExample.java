package com.huzi.example.consumer;


import com.huzi.example.common.service.UserService;
import com.huzi.example.common.model.User;
import com.huzi.hurpc.proxy.ServiceProxyFactory;

public class EasyConsumerExample {
    public static void main(String[] args) {
        // 动态代理
        //getProxy 这个方法的主要用途是创建一个代理对象，该对象实现了指定的接口或类。
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        // todo 需要获取 UserService 的实现类对象
        User user = new User();
        user.setName("huzi");
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println("新用户名：" + newUser.getName());
        }else{
            System.out.println("user=null");
        }
    }
}
