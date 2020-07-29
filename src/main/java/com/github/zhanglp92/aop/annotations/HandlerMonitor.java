package com.github.zhanglp92.aop.annotations;

import java.lang.annotation.*;

/**
 * 责任链执行单元监控
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerMonitor {
}
