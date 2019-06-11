package com.puffer.util.mq;

import com.google.common.eventbus.AsyncEventBus;
import org.testng.annotations.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MqConsumerTest {

    @Test
    public void testLister() {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(128),
                new ThreadPoolExecutor.AbortPolicy());

        // Executors.new
        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(100));

        asyncEventBus.register(new MqConsumer());

        asyncEventBus.post("测试消息");

    }
}