package com.github.zhanglp92.config;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;

/**
 * 应用执行器
 */
public interface ApplyExecute {
    void execute(Context context, Compose compose) throws Throwable;
}
