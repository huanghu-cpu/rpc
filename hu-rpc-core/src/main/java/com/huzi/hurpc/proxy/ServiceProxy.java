package com.huzi.hurpc.proxy;
import cn.hutool.core.collection.CollUtil;
import com.huzi.hurpc.RpcApplication;
import com.huzi.hurpc.config.RpcConfig;
import com.huzi.hurpc.constant.RpcConstant;
import com.huzi.hurpc.fault.retry.RetryStrategy;
import com.huzi.hurpc.fault.retry.RetryStrategyFactory;
import com.huzi.hurpc.fault.tolerant.TolerantStrategy;
import com.huzi.hurpc.fault.tolerant.TolerantStrategyFactory;
import com.huzi.hurpc.loadbalancer.LoadBalancer;
import com.huzi.hurpc.loadbalancer.LoadBalancerFactory;
import com.huzi.hurpc.model.RpcRequest;
import com.huzi.hurpc.model.RpcResponse;
import com.huzi.hurpc.model.ServiceMetaInfo;
import com.huzi.hurpc.registry.Registry;
import com.huzi.hurpc.registry.RegistryFactory;
import com.huzi.hurpc.serializer.Serializer;
import com.huzi.hurpc.serializer.SerializerFactory;
import com.huzi.hurpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 服务代理（JDK 动态代理）
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @learn <a href="https://codefather.cn">编程宝典</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            //负载均衡
            LoadBalancer loadBalancer= LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            //将调用方法名（请求路径）作为负载均衡参数
            Map<String,Object> requestParams=new HashMap<>();
            requestParams.put("methodName",method.getName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            //ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
            // 发送 TCP 请求
            // 使用重试机制
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
            } catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }

}
