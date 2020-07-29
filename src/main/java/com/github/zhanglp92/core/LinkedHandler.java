package com.github.zhanglp92.core;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import lombok.Data;
import lombok.NonNull;

/**
 * 责任链静态代理
 */
@Data
public class LinkedHandler {
    /**
     * 后继节点
     */
    private LinkedHandler successor;

    /**
     * 当前handler
     */
    @NonNull
    private RuleHandler handler;

    /**
     * 执行链条
     */
    final public void apply(Context context, Compose compose) throws Throwable {
        handler.handler(context, compose);
        if (this.successor != null) {
            successor.apply(context, compose);
        }
    }
}