package com.github.zhanglp92.launcher;

import com.github.zhanglp92.aop.HandlerMonitorAspectConfigurable;
import com.github.zhanglp92.config.ApplyConfig;
import com.github.zhanglp92.config.ApplyMonitorConfigurable;
import com.github.zhanglp92.monitor.LogHandlerMonitor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 框架配置
 */
@Component
@Log4j2
public class DefaultApplyConfig extends ApplyConfig implements ApplyMonitorConfigurable {

    @Override
    public void setApplyHandlerMonitor(HandlerMonitorAspectConfigurable configurable) {
        configurable.setApplyHandlerMonitor(new LogHandlerMonitor());
    }

    @Override
    public @NotNull String[] getDefaultLinkedHandlerNameList() {
        return new String[]{"sampleHandler"};
    }
}
