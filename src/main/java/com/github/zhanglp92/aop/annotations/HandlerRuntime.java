package com.github.zhanglp92.aop.annotations;

import java.lang.annotation.*;

/**
 * handler 运行情况控制
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerRuntime {

    /**
     * 是否启动模块运行时控制
     */
    boolean enabled() default true;

    /**
     * 使用独立线程池运行当前模块
     */
    boolean useAloneThreadPool() default false;

    /**
     * 模块超时时间ms, 默认不设置
     */
    long timeout() default -1;

    /**
     * 默认唯一标识(默认class name)
     */
    String identify() default "";
}
