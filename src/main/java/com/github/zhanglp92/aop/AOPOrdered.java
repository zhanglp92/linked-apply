package com.github.zhanglp92.aop;

/**
 * 多个切面同时存在时, 优先级定义(值越小, 优先执行)
 */
public enum AOPOrdered {
    /**
     * 监控
     */
    MONITOR,

    /**
     * 用户方法
     */
    PROCEED
}
