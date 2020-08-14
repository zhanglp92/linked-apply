package com.github.zhanglp92.runtime;

import com.alibaba.fastjson.JSONObject;
import com.github.zhanglp92.utils.Assert;
import com.github.zhanglp92.utils.ObjectUtils;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义执行器
 */
public class ThreadPoolTaskExecutor extends org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor {

    /**
     * 当前线程池配置
     */
    private JSONObject config;

    public ThreadPoolTaskExecutor(JSONObject config) {
        super();
        this.update(config);
    }

    private void update(JSONObject config) {
        // 更新成功之后更新配置
        this.buildThreadPool(config);
        this.config = config;
    }

    private void buildThreadPool(JSONObject config) {
        // 不支持动态更新的属性
        this.staticUpdate();

        // 支持动态修改的属性
        this.dynamicUpdate();

        // 初始化线程池
        this.initialize();
    }

    /**
     * 支持动态修改的属性
     */
    private void dynamicUpdate() {
        // 标识符不能为空
        String identity = config.getString("identity");
        Assert.notBlack(identity, "config hasn't identity");
        this.setThreadNamePrefix(identity);

        this.setCorePoolSize(ObjectUtils.getDefault(config.getInteger("corePoolSize"), 1));
        this.setMaxPoolSize(ObjectUtils.getDefault(config.getInteger("maxPoolSize"), 2));
        this.setKeepAliveSeconds(ObjectUtils.getDefault(config.getInteger("keepAliveSeconds"), 10));
    }

    private void staticUpdate() {
        // 空闲时回收核心线程
        this.setAllowCoreThreadTimeOut(ObjectUtils.getDefault(config.getBoolean("allowCoreThreadTimeOut"), false));
        this.setWaitForTasksToCompleteOnShutdown(true);
        this.setQueueCapacity(ObjectUtils.getDefault(config.getInteger("queueCapacity"), 10));

        // 设置拒绝策略
        RejectedExecutionHandler rejectedExecutionHandler;
        switch (config.getString("rejectedExecution")) {
            case "CallerRunsPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case "DiscardPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            case "DiscardOldestPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            default:
                rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
        }
        this.setRejectedExecutionHandler(rejectedExecutionHandler);

        this.setTaskDecorator(runnable -> {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    MDC.setContextMap(contextMap);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        });
    }

}
