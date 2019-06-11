package com.puffer.util.mq;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * 消息消费者
 *
 * @author buyi
 * @date 2019年06月10日 21:36:31
 * @since 1.0.0
 */
public class MqConsumer {

    @Subscribe
    @AllowConcurrentEvents
    public void lister(String content){
        System.out.println("进入消费："+content);
    }
}
