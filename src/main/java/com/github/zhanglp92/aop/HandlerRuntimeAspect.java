package com.github.zhanglp92.aop;

import com.github.zhanglp92.aop.annotations.HandlerRuntime;
import com.github.zhanglp92.runtime.HandlerRuntimeContext;
import com.github.zhanglp92.runtime.RFuture;
import com.github.zhanglp92.runtime.ThreadPool;
import com.github.zhanglp92.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Aspect
@Component
public class HandlerRuntimeAspect implements Ordered {

    /**
     * 运行环境
     */
    private ThreadLocal<HandlerRuntimeContext> runtimeContext;

    public HandlerRuntimeAspect() {
        this.runtimeContext = new ThreadLocal<>();
    }

    /**
     * 初始化运行环境
     */
    private void initRuntimeContext(ProceedingJoinPoint proceedingJoinPoint, HandlerRuntime handlerRuntime) {
        HandlerRuntimeContext context = new HandlerRuntimeContext();
        context.setIdentify(StringUtils.isEmpty(handlerRuntime.identify()) ? proceedingJoinPoint.getClass().getSimpleName() : handlerRuntime.identify());

        this.runtimeContext.remove();
        this.runtimeContext.set(context);
    }

    @Override
    public int getOrder() {
        return AOPOrdered.PROCEED.ordinal();
    }

    @Pointcut("execution(public void handler(com.github.zhanglp92.context.Context, com.github.zhanglp92.compose.Compose) throws Throwable) && @annotation(handlerRuntime)")
    public void pointcut(HandlerRuntime handlerRuntime) {
    }

    @Around(value = "pointcut(handlerRuntime)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, HandlerRuntime handlerRuntime) throws Throwable {
        if (!handlerRuntime.enabled()) {
            return proceedingJoinPoint.proceed();
        }

        // 初始化环境
        this.initRuntimeContext(proceedingJoinPoint, handlerRuntime);

        if (handlerRuntime.useAloneThreadPool()) {
            return this.aloneProceed(proceedingJoinPoint, handlerRuntime);
        } else {
            return proceedingJoinPoint.proceed();
        }
    }

    /**
     * 独立运行模块
     */
    private Object aloneProceed(ProceedingJoinPoint proceedingJoinPoint, HandlerRuntime handlerRuntime) throws Throwable {
        HandlerRuntimeContext context = this.runtimeContext.get();
        AtomicReference<Throwable> throwable = null;

        RFuture<Object> future = ThreadPool.submit(context.getIdentify(), () -> {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                throwable.set(e);
            }
            return null;
        });

        long timeout = handlerRuntime.timeout();
        if (timeout < 0) {
            return future.getFuture().get();
        } else {
            return future.getFuture().get(timeout, TimeUnit.MILLISECONDS);
        }
    }
}
