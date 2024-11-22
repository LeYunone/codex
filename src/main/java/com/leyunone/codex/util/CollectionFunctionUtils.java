package com.leyunone.codex.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @create 2022/9/6
 */
public class CollectionFunctionUtils {

    /**
     * 将list转换为map
     *
     * @param ls
     * @param rule 转换key规则
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> mapTo(List<T> ls, Function<T, R> rule) {
        if (ls == null || ls.size() == 0) {
            return new HashMap<>();
        }
        return ls.stream().collect(Collectors.toMap(rule, x -> x, (k1, k2) -> k1));
    }

    public static <T, R> Map<R, List<T>> groupTo(List<T> ls, Function<T, R> rule) {
        if (ls == null || ls.size() == 0) {
            return new HashMap<>();
        }
        return ls.stream().collect(Collectors.groupingBy(rule));
    }

    public static <T, R> Set<R> setTo(List<T> ls, Function<T, R> rule) {
        if (ls == null || ls.size() == 0) {
            return new HashSet<>();
        }
        return ls.stream().map(rule).collect(Collectors.toSet());
    }
}
