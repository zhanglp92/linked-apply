package com.github.zhanglp92.aop;

import com.github.zhanglp92.monitor.ApplyHandlerMonitor;
import lombok.NonNull;

/**
 * 监控自定义接口
 */
public interface HandlerMonitorAspectConfigurable {

    void setApplyHandlerMonitor(@NonNull ApplyHandlerMonitor applyHandlerMonitor);
}
