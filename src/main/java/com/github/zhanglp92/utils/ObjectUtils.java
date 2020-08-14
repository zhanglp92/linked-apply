package com.github.zhanglp92.utils;

public class ObjectUtils extends org.springframework.util.ObjectUtils {

    /**
     * 获取对象默认值
     */
    public static <T> T getDefault(T val, T def) {
        return val == null ? def : val;
    }
}
