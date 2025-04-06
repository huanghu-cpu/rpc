package com.huzi.hurpc.registry;

import com.huzi.hurpc.model.ServiceMetaInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;


/**
 * 注册中心服务本地缓存（支持多个服务）
 *
 * @author <a href="https://github.com/liyupi">coder_yupi</a>
 * @from <a href="https://yupi.icu">编程导航学习圈</a>
 * @learn <a href="https://codefather.cn">yupi 的编程宝典</a>
 */
public class RegistryServiceMultiCache {

    /**
     * 服务缓存
     */
    Map<String, List<ServiceMetaInfo>> serviceCache = new ConcurrentHashMap<>();

    /**
     * 写缓存
     *
     * @param serviceKey 服务键名
     * @param newServiceCache 更新后的缓存列表
     * @return
     */
    void writeCache(String serviceKey, List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache.put(serviceKey, newServiceCache);
    }

    /**
     * 读缓存
     *
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> readCache(String serviceKey) {
        return this.serviceCache.get(serviceKey);
    }

    /**
     * 清空缓存
     */
    void clearCache(String serviceKey) {
        this.serviceCache.remove(serviceKey);
    }
}



//public class RegistryServiceCache {
//    /**
//     * 服务缓存
//     */
//    List<ServiceMetaInfo> serviceCache;
//
//    /**
//     * 写缓存
//     *
//     * @param newServiceCache
//     * @return
//     */
//    void writeCache(List<ServiceMetaInfo> newServiceCache) {
//        this.serviceCache = newServiceCache;
//    }
//
//    /**
//     * 读缓存
//     *
//     * @return
//     */
//    List<ServiceMetaInfo> readCache() {
//        return this.serviceCache;
//    }
//
//    /**
//     * 清空缓存
//     */
//    void clearCache() {
//        this.serviceCache = null;
//    }
//}




