package com.github.zhanglp92.monitor;

/**
 * 应用Handler监控
 */
public interface ApplyHandlerMonitor {

    /**
     * 自定义监控
     */
    void monitor(HandlerMonitorContext monitorContext);

    /**
     * 自定义异常监控
     */
    void monitorException(HandlerMonitorContext monitorContext);
}
