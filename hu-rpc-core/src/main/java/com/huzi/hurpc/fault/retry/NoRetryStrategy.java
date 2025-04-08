package com.huzi.hurpc.fault.retry;

import com.huzi.hurpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试策略
 */
public class NoRetryStrategy implements RetryStrategy {

    /**
     * 重试
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
