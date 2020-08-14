package com.github.zhanglp92.runtime;

import lombok.Getter;

import java.util.concurrent.Future;

/**
 * 自定义中控Future
 */
public class RFuture<V> {

    /**
     * 线程池提交后句柄
     */
    @Getter
    private Future<V> future;

    public RFuture(Future<V> future) {
        this.future = future;
    }
}
