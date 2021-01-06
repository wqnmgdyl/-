package com.kh.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author han.ke
 */
public class CommonUtils {
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
