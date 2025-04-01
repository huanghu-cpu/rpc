package com.huzi.example.provider;


import com.huzi.example.common.service.UserService;
import com.huzi.hurpc.registry.LocalRegistry;
import com.huzi.hurpc.server.HttpServer;
import com.huzi.hurpc.server.VertxHttpServer;

public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}

