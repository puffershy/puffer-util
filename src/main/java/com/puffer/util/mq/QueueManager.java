package com.puffer.util.mq;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.Maps;

/**
 * 队列管理类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018下午5:32:13
 */
public class QueueManager {

	private static final Map<String, ConcurrentLinkedQueue<Object>> messageQueue = Maps.newHashMap();

	/**
	 * 添加消息到队列
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:35:58
	 * @param queueName
	 *            队列名
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean push(String queueName, Object value) {
		ConcurrentLinkedQueue<Object> queue = messageQueue.get(queueName);
		if (queue == null) {
			queue = createQueue(queueName);
		}

		return queue.add(value);
	}

	/**
	 * 从队列中获取一个消息
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:43:26
	 * @param queueName
	 * @return
	 */
	public static Object poll(String queueName) {
		ConcurrentLinkedQueue<Object> queue = messageQueue.get(queueName);
		if (queue == null) {
			return null;
		}

		if (queue.isEmpty()) {
			return null;
		}

		return queue.poll();
	}

	/**
	 * 创建队列
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:40:38
	 * @param queueName
	 */
	private static synchronized ConcurrentLinkedQueue<Object> createQueue(String queueName) {
		ConcurrentLinkedQueue<Object> queue = messageQueue.get(queueName);

		if (queue == null) {
			queue = new ConcurrentLinkedQueue<Object>();
			messageQueue.put(queueName, queue);
		}

		return queue;
	}
}
