package com.github.zhanglp92.aop;

/**
 * 自定义AOP执行顺序
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
