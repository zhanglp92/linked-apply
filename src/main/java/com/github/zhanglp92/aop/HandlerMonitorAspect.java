package com.github.zhanglp92.aop;


import com.github.zhanglp92.aop.annotations.HandlerMonitor;
import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import com.github.zhanglp92.monitor.ApplyHandlerMonitor;
import com.github.zhanglp92.monitor.HandlerMonitorContext;
import com.github.zhanglp92.monitor.LogHandlerMonitor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 基于spring实现
 */

@Log4j2
@Aspect
@Component
public class HandlerMonitorAspect implements HandlerMonitorAspectConfigurable, Ordered {

    /**
     * 监控上下问内容
     */
    private ThreadLocal<HandlerMonitorContext> monitorContextThreadLocal;

    /**
     * 监控自定义方法, 默认使用Log记录的方式
     */
    private ApplyHandlerMonitor applyHandlerMonitor = new LogHandlerMonitor();

    public HandlerMonitorAspect() {
        this.monitorContextThreadLocal = new ThreadLocal<>();
    }

    /**
     * 当有多个AOP时, order执行先后顺序, 值越小最先执行, 最后结束
     */
    @Override
    public int getOrder() {
        return AOPOrdered.MONITOR.ordinal();
    }

    @Override
    public void setApplyHandlerMonitor(@NonNull ApplyHandlerMonitor applyHandlerMonitor) {
        this.applyHandlerMonitor = applyHandlerMonitor;
    }

    /**
     * 定义切点位置(只处理handler方法)
     * * 以下方法执行顺序:
     * * 正常: doAround before --> doBefore --> doAround after --> doAfter --> doAfterReturning
     * * 异常: doAround before --> doBefore --> doAfter --> doAfterThrowing
     */

//    @Pointcut("execution(* *(..)) && @annotation(com.github.zhanglp92.aop.annotations.HandlerMonitor)")
//    public void pointcut() {
//    }
    @Pointcut("execution(public void handler(com.github.zhanglp92.context.Context, com.github.zhanglp92.compose.Compose) throws Throwable) && @annotation(handlerMonitor)")
    public void pointcut(HandlerMonitor handlerMonitor) {
    }

    /**
     * 定义环绕处理方式
     */
    @Around(value = "pointcut(handlerMonitor)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, HandlerMonitor handlerMonitor) throws Throwable {
        // 初始化监控变量
        monitorContextThreadLocal.remove();
        monitorContextThreadLocal.set(new HandlerMonitorContext()
                .setBeginTime(System.currentTimeMillis())
                .setHandlerContext((Context) proceedingJoinPoint.getArgs()[0])
                .setHandlerCompose((Compose) proceedingJoinPoint.getArgs()[1])
                .setSignature(proceedingJoinPoint.getSignature().getDeclaringTypeName())
        );

        // handler方法执行
        return proceedingJoinPoint.proceed();
    }

    @Before(value = "pointcut(handlerMonitor)")
    public void before(JoinPoint joinPoint, HandlerMonitor handlerMonitor) {
    }

    @After(value = "pointcut(handlerMonitor)")
    public void after(JoinPoint joinPoint, HandlerMonitor handlerMonitor) {
        HandlerMonitorContext handlerMonitorContext = monitorContextThreadLocal.get();
        handlerMonitorContext.setEndTime(System.currentTimeMillis());
    }

    @AfterReturning(value = "pointcut(handlerMonitor)")
    public void afterReturning(JoinPoint joinPoint, HandlerMonitor handlerMonitor) {
        this.applyHandlerMonitor.monitor(this.monitorContextThreadLocal.get());
    }

    @AfterThrowing(value = "pointcut(handlerMonitor)")
    public void afterThrowing(JoinPoint joinPoint, HandlerMonitor handlerMonitor) {
        this.applyHandlerMonitor.monitorException(this.monitorContextThreadLocal.get());
    }
}
