package com.kh.ad.index;

/**
 * @author han.ke
 */
public interface IndexAware<K, V> {
    /**
     * 获取索引
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 增加索引
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 修改索引
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);
}
