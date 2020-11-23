package com.glv.project.system.modules.redis.config;

import com.glv.project.system.modules.redis.handler.RedisKeyExpirationHandler;
import com.glv.project.system.modules.web.component.SpringContextHolder;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {


    /**
     * Creates new {@link org.springframework.data.redis.connection.MessageListener}
     * for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpirationListener(
            RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对 redis 数据失效事件，进行数据处理
     *
     * @param message 消息
     * @param pattern 模式
     */
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        String key = message.toString();
        // 只监控指定的键过期事件
        String prefix = "monitor:";
        if (StringUtils.startsWith(key, prefix)) {
            Map<String, RedisKeyExpirationHandler> redisKeyExpirationHandlerMap =
                    SpringContextHolder.getBeansOfType(RedisKeyExpirationHandler.class);
            if (redisKeyExpirationHandlerMap.containsKey(key)) {
                redisKeyExpirationHandlerMap.get(key).handle(key);
            }
        }
    }
}
