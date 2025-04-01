package com.huzi.example.provider;

import com.huzi.example.common.service.UserService;
import com.huzi.hurpc.RpcApplication;
import com.huzi.hurpc.registry.LocalRegistry;
import com.huzi.hurpc.server.HttpServer;
import com.huzi.hurpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 *
 * @author
 * @learn
 * @from
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }

}

