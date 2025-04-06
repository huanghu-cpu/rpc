package com.huzi.example.provider;

import com.huzi.example.common.service.UserService;
import com.huzi.hurpc.RpcApplication;
import com.huzi.hurpc.config.RegistryConfig;
import com.huzi.hurpc.config.RpcConfig;
import com.huzi.hurpc.model.ServiceMetaInfo;
import com.huzi.hurpc.registry.LocalRegistry;
import com.huzi.hurpc.server.HttpServer;
import com.huzi.hurpc.server.VertxHttpServer;
import com.huzi.hurpc.registry.Registry;
import com.huzi.hurpc.registry.RegistryFactory;
import com.huzi.hurpc.server.tcp.VertxTcpServer;


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
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);

        // 启动 web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}

