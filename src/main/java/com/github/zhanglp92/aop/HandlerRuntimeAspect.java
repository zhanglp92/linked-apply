package com.github.zhanglp92.aop;

import com.github.zhanglp92.aop.annotations.HandlerRuntime;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class HandlerRuntimeAspect {

    @Pointcut("execution(public void handler(com.github.zhanglp92.context.Context, com.github.zhanglp92.compose.Compose) throws Throwable) && @annotation(handlerRuntime)")
    public void pointcut(HandlerRuntime handlerRuntime) {
    }

    @Around(value = "pointcut(handlerRuntime)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, HandlerRuntime handlerRuntime) throws Throwable {
        if (!handlerRuntime.enabled()) {
            return proceedingJoinPoint.proceed();
        }
        return this.syncProceed(proceedingJoinPoint, handlerRuntime);
    }

    private Object syncProceed(ProceedingJoinPoint proceedingJoinPoint, HandlerRuntime handlerRuntime) throws Throwable {
        // TODO: 使用独立线程运行
        log.warn("TODO, use alone thread runtime");
        return proceedingJoinPoint.proceed();
    }
}
