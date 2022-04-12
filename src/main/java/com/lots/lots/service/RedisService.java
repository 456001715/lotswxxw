package com.lots.lots.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service
 *
 * @author lots
 */
public interface RedisService {

    /**
     * 保存属性
     *
     * @param key:   k
     * @param value: b
     * @param time:  time
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:59
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key:   k
     * @param value: y
     * @author: lots
     * @return: void
     * @date: 2021/4/29 14:00
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key: k
     * @author: lots
     * @return: java.lang.Object
     * @date: 2021/4/29 14:00
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key: k
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:00
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys: keys
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:00
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key:  k
     * @param time: time
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:00
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key: k
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:00
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     *
     * @param key: k
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:01
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     *
     * @param key:   k
     * @param delta: delta
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:01
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     *
     * @param key:
     * @param delta:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:01
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     *
     * @param key:
     * @param hashKey:
     * @author: lots
     * @return: java.lang.Object
     * @date: 2021/4/29 14:01
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key:
     * @param hashKey:
     * @param value:
     * @param time:
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:01
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key:
     * @param hashKey:
     * @param value:
     * @author: lots
     * @return: void
     * @date: 2021/4/29 14:01
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     *
     * @param key:
     * @author: lots
     * @return: java.util.Map<java.lang.Object, java.lang.Object>
     * @date: 2021/4/29 14:01
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     *
     * @param key:
     * @param map:
     * @param time:
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:01
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key:
     * @param map:
     * @author: lots
     * @return: void
     * @date: 2021/4/29 14:01
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     *
     * @param key:
     * @param hashKey:
     * @author: lots
     * @return: void
     * @date: 2021/4/29 14:02
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key:
     * @param hashKey:
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:02
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     *
     * @param key:
     * @param hashKey:
     * @param delta:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param key:
     * @param hashKey:
     * @param delta:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     *
     * @param key:
     * @author: lots
     * @return: java.util.Set<java.lang.Object>
     * @date: 2021/4/29 14:02
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key:
     * @param values:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key:
     * @param time:
     * @param values:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     *
     * @param key:
     * @param value:
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 14:02
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     *
     * @param key:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key:
     * @param values:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:02
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     *
     * @param key:
     * @param start:
     * @param end:
     * @author: lots
     * @return: java.util.List<java.lang.Object>
     * @date: 2021/4/29 14:03
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key:
     * @param index:
     * @author: lots
     * @return: java.lang.Object
     * @date: 2021/4/29 14:03
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key:
     * @param value:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     *
     * @param key:
     * @param value:
     * @param time:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     *
     * @param key:
     * @param values:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     *
     * @param key:
     * @param time:
     * @param values:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     *
     * @param key:
     * @param count:
     * @param value:
     * @author: lots
     * @return: java.lang.Long
     * @date: 2021/4/29 14:03
     */
    Long lRemove(String key, long count, Object value);
}