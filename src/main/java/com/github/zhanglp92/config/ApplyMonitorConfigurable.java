package com.github.zhanglp92.config;

import com.github.zhanglp92.aop.HandlerMonitorAspectConfigurable;

/**
 * 应用配置接口
 */
public interface ApplyMonitorConfigurable {

    /**
     * 监控配置
     */
    void setApplyHandlerMonitor(HandlerMonitorAspectConfigurable configurable);
}
