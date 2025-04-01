package com.huzi.example.consumer;

import com.huzi.hurpc.config.RpcConfig;
import com.huzi.hurpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author
 * @learn
 * @from
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);

    }
}

