package com.lots.lots.controller.auth;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 监听redis过期key
 *
 * @author lots
 */
@Component
public class CustomerKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public CustomerKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        String key = message.toString();
        String likeName = "key";
        //匹配需要的key，进行处理
        if (!key.startsWith(likeName)) {
            return;
        }
        //加锁（不同的key过期获取的锁是不一样的）
        String lockKey = "lock_" + key;
        boolean lock = lock(lockKey);
        if (!lock) {
            System.out.println("===return" + key + "===");
            return;
        }
        try {
            System.out.println("监听到key:" + key + "过期");
            //释放锁(可以不用释放)
            // 这里睡眠5秒后解锁，防止程序太快，导致服务1已经执行完毕，服务2才刚刚开始获取锁
            Thread.sleep(5000);
            unlock(lockKey);
        } catch (InterruptedException e) {
        }

    }

    /**
     * 在分布式场景下，该监听器会监听多次，因此需要使用锁(防止同一个key被监听执行多次)
     *
     * @param lockKey:
     * @author: lots
     * @return: java.lang.Boolean
     * @date: 2021/4/29 16:27
     */
    private Boolean lock(String lockKey) {
        Long timeOut2 = new Long(-2);
        Long timeOut = stringRedisTemplate.getExpire(lockKey);
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;

            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().setIfAbsent(lockKey, "lock");
                if (timeOut == null || timeOut.equals(timeOut2)) {
                    operations.expire(lockKey, 30, TimeUnit.SECONDS);
                }
                exec = operations.exec();
                if (exec.size() > 0) {
                    return (Boolean) exec.get(0);
                }
                return false;
            }
        };
        return stringRedisTemplate.execute(sessionCallback);
    }

    private void unlock(String lockKey) {
        stringRedisTemplate.delete(lockKey);
    }

}
