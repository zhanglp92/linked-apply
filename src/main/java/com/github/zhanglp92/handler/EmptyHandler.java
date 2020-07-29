package com.github.zhanglp92.handler;

import com.github.zhanglp92.aop.annotations.HandlerMonitor;
import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import com.github.zhanglp92.core.RuleHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmptyHandler extends RuleHandler {

    private static RuleHandler handler = new EmptyHandler();

    public static RuleHandler getInstance() {
        return handler;
    }

    @Override
    @HandlerMonitor
    public void handler(Context context, Compose compose) throws Throwable {
        log.info("run empty handler");
    }
}
