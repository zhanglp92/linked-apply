package com.github.zhanglp92.runtime;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 线程池封装
 */
public class ThreadPool {

    // 单利模式
    private static ThreadPool instance;

    // 线程池集合
    private Map<String, ThreadPoolTaskExecutor> handlerThreadPool;

    static {
        instance = new ThreadPool();
    }

    private ThreadPool() {

    }

    /**
     * 获取线程池, 如果不存在拉去最新配置创建
     */
    private ThreadPoolTaskExecutor getThreadPool(String identity) {
        ThreadPoolTaskExecutor poolTaskExecutor = this.handlerThreadPool.get(identity);
        if (poolTaskExecutor == null) {
            poolTaskExecutor = genThreadPool(getThreadPoolConfig(identity));
        }
        return poolTaskExecutor;
    }

    /**
     * 生成线程池
     *
     * @param config 线程池配置
     */
    private ThreadPoolTaskExecutor genThreadPool(JSONObject config) {
        return new ThreadPoolTaskExecutor(config);
    }

    /**
     * 获取线程池配置
     * TODO: 从配置管理中心获取配置
     */
    private JSONObject getThreadPoolConfig() {
        return new JSONObject();
    }

    /**
     * 获取线程池配置
     * TODO: 从配置管理中心获取配置
     */
    private JSONObject getThreadPoolConfig(String identity) {
        return new JSONObject();
    }

    /**
     * 任务提交, TODO: 实现
     */
    public static <T> RFuture<T> submit(String identity, Callable<T> task) throws Throwable {
        return new RFuture<>(instance.getThreadPool(identity).submit(task));
    }
}
