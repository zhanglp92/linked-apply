package com.github.zhanglp92.aop;

import com.github.zhanglp92.monitor.ApplyHandlerMonitor;
import lombok.NonNull;

/**
 * 监控自定义接口
 */
public interface HandlerMonitorAspectConfigurable {

    /**
     * 设置监控实例
     *
     * @param applyHandlerMonitor 监控实例接口
     */
    void setApplyHandlerMonitor(@NonNull ApplyHandlerMonitor applyHandlerMonitor);
}
