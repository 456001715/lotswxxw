package com.lots.lots.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @name: SynchronizedByKeyUtil 订单同步锁 如果为同一个订单号，便加锁互斥的锁
 * @author: lots
 * @date: 2022/1/24 17:01
 */
@Component
public class SynchronizedByKeyUtil {

    /**
     * 选择线程安全的map 可以方便超过5000并发的时候 出现极端情况  选择ReentrantLock 方便队列数量为0的时候删除。
     *
     * @author: lots
     * @param null:
     * @return: null
     * @date: 2022/1/24 17:07
     */
    Map<String, ReentrantLock> mutexCache = new ConcurrentHashMap<>();

    public void exec(String key, Runnable statement) {
        ReentrantLock mutex4Key = null;
        ReentrantLock mutexInCache;
        do {
            if (mutex4Key != null) {
                mutex4Key.unlock();
            }
            //如果key不存在便会添加
            mutex4Key = mutexCache.computeIfAbsent(key, k -> new ReentrantLock());
            mutex4Key.lock();
            mutexInCache = mutexCache.get(key);
            /*
              1. mutexCache == null
              2. mutexKey != mutexCache
             */
        } while (mutexInCache == null || mutex4Key != mutexInCache);
            try {
                statement.run();
            } finally {
                mutex4Key.unlock();
                if (mutex4Key.getQueueLength() == 0) {
                    mutexCache.remove(key);
                }

            }

    }

    public static void main(String[] args) {
        SynchronizedByKeyUtil synchronizedByKeyUtil = new SynchronizedByKeyUtil();

        String orderId = "201232156";
        synchronizedByKeyUtil.exec(orderId, () -> {
            System.out.println("开始");
            //、 订单逻辑
            System.out.println("结束");
        });
    }


}
