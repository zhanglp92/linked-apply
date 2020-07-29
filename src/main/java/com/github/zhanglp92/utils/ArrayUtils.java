package com.github.zhanglp92.utils;

public class ArrayUtils extends com.sun.tools.javac.util.ArrayUtils {

    public static <T> boolean isEmpty(T[] array) {
        return 0 == size(array);
    }

    public static <T> int size(T[] array) {
        return array != null ? array.length : 0;
    }
}
