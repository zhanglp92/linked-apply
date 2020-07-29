package com.github.zhanglp92.monitor;

import lombok.extern.log4j.Log4j2;

/**
 * 默认本地日志文件监控Handler
 */
@Log4j2
public class LogHandlerMonitor implements ApplyHandlerMonitor {

    @Override
    public void monitor(HandlerMonitorContext monitorContext) {
        log.info("handler monitor begin={}, ut={}", monitorContext.getBeginTime(), monitorContext.ut());
    }

    @Override
    public void monitorException(HandlerMonitorContext monitorContext) {
        log.error("handler monitor begin={}, ut={}", monitorContext.getBeginTime(), monitorContext.ut());
    }
}
