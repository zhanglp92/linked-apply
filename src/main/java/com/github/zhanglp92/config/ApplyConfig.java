package com.github.zhanglp92.config;

import com.github.zhanglp92.aop.HandlerMonitorAspectConfigurable;
import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import com.github.zhanglp92.core.LinkedHandler;
import com.github.zhanglp92.core.RuleHandler;
import com.github.zhanglp92.handler.EmptyHandler;
import com.github.zhanglp92.utils.ArrayUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

/**
 * 应用启动
 */
@Log4j2
public abstract class ApplyConfig implements ApplyExecute, ApplicationContextAware {

    /**
     * 默认链
     */
    private LinkedHandler defLinkedHead;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 初始化自定义HandlerMonitor配置
        applicationContext.getBeansOfType(HandlerMonitorAspectConfigurable.class).forEach((k, bean) -> {
            if (this instanceof ApplyMonitorConfigurable) {
                ((ApplyMonitorConfigurable) this).setApplyHandlerMonitor(bean);
            }
        });

        // 初始化责任链
        defLinkedHead = this.resetLinked(new LinkedHandler(EmptyHandler.getInstance()), applicationContext, this.getDefaultLinkedHandlerNameList());
    }

    /**
     * 同一层Handler
     */
    private LinkedHandler resetLinked(LinkedHandler curHandler, ApplicationContext applicationContext, String[] linkedHandlerNameList) {
        if (ArrayUtils.isEmpty(linkedHandlerNameList)) {
            return curHandler;
        }
        @NonNull
        LinkedHandler pos = curHandler;
        for (String linkedHandlerName : linkedHandlerNameList) {
            pos = resetLinked(pos, applicationContext, linkedHandlerName).getSuccessor();
        }
        return curHandler;
    }

    private LinkedHandler resetLinked(LinkedHandler curHandler, ApplicationContext applicationContext, String linkedHandlerName) {
        linkedHandlerName = StringUtils.trimAllWhitespace(linkedHandlerName);
        if (!StringUtils.isEmpty(linkedHandlerName)) {
            curHandler.setSuccessor(new LinkedHandler(applicationContext.getBean(linkedHandlerName, RuleHandler.class)));
        }
        return curHandler;
    }

    /**
     * 获取自定义责任链列表
     */
    public abstract @NotNull String[] getDefaultLinkedHandlerNameList();

    /**
     * 执行责任链
     */
    @Override
    final public void execute(Context context, Compose compose) throws Throwable {
        defLinkedHead.apply(context, compose);
    }
}
