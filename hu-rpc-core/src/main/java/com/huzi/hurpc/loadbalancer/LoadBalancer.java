package com.huzi.hurpc.loadbalancer;

import com.huzi.hurpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（消费端调用）
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
