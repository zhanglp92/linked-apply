package com.github.zhanglp92.monitor;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.context.Context;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

/**
 * 处理单元监控上下文参数
 */
@Log4j2
@Data
@Accessors(chain = true)
public class HandlerMonitorContext {
    /**
     * 方法开始结束时间
     */
    private long beginTime, endTime;

    /**
     * handler context
     */
    private Context handlerContext;

    /**
     * handler compose
     */
    private Compose handlerCompose;

    /**
     * 方法签名
     */
    private String signature;

    /**
     * 方法耗时
     */
    public long ut() {
        return this.endTime - this.beginTime;
    }

    public HandlerMonitorContext reset() {
        this.beginTime = 0;
        this.endTime = 0;
        this.handlerContext = null;
        this.handlerCompose = null;
        return this;
    }
}
