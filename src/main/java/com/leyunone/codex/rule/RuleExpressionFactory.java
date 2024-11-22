package com.leyunone.codex.rule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 18:02
 */
public class RuleExpressionFactory {

    public static Map<String, Object> map = new ConcurrentHashMap<>();

    public static void regist(String key, Object o) {
        map.put(key, o);
    }

    public static <T> T get(String key, Class<T> clazz) {
        return (T) map.get(key);
    }
}
