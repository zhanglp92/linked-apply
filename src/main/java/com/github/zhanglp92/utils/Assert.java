package com.github.zhanglp92.utils;

import org.springframework.lang.Nullable;

/**
 * 断言
 */
public class Assert extends org.springframework.util.Assert {

    /**
     * 空串时异常
     */
    public static void notBlack(@Nullable String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }
}
