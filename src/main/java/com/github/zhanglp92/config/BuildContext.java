package com.github.zhanglp92.config;

import com.github.zhanglp92.context.Context;

@FunctionalInterface
public interface BuildContext {
    /**
     * 构造
     */
    Context build();
}
