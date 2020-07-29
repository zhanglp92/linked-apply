package com.github.zhanglp92.aop.annotations;

import java.lang.annotation.*;

/**
 * handler独立线程运行
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerRuntime {
    boolean enabled() default false;
}
