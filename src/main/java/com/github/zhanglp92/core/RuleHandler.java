package com.github.zhanglp92.core;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;

/**
 * 责任链抽象节点
 */
public abstract class RuleHandler {

    /**
     * handler核型执行单元
     */
    public abstract void handler(Context context, Compose compose) throws Throwable;
}
