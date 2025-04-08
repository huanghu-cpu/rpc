package com.huzi.hurpc.config;

import com.huzi.hurpc.fault.retry.RetryStrategyKeys;
import com.huzi.hurpc.loadbalancer.LoadBalancerKeys;
import lombok.Data;
import com.huzi.hurpc.serializer.SerializerKeys;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "hu-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;


    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();


    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;
}

