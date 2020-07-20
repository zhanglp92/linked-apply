package com.github.zhanglp92.core;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import lombok.Generated;

/**
 * 责任链抽象节点
 */
public abstract class RuleHandler {
    /**
     * next handler
     */
    @Generated
    protected RuleHandler successor;

    /**
     * 执行逻辑
     */
    protected void apply(Context context, Compose compose) {
        this.handler(context, compose);
        if (this.successor != null) {
            this.successor.apply(context, compose);
        }
    }

    public abstract void handler(Context context, Compose compose);
}
