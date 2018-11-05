package com.buyi.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流监控器<br>
 * 限制并发数量
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017上午11:05:33
 */
public class FlowMonitor {

	/**
	 * 最大线程数
	 */
	private int maxFlowSize;

	private static final int DEFAULT_MAX_FLOW_SIZE = 100;

	/**
	 * 正在运行的计数器
	 */
	private AtomicInteger runningAtomic = new AtomicInteger();

	/**
	 * 失败的记录数
	 */
	private AtomicInteger loseAtomic = new AtomicInteger();

	/**
	 * 通过的记录数
	 */
	private AtomicInteger passAtomic = new AtomicInteger();

	private FlowMonitor(int maxFlowSize) {
		this.maxFlowSize = maxFlowSize;
	}

	/**
	 * 新建实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午2:59:32
	 * @return
	 */
	public static FlowMonitor newInstance() {
		return new FlowMonitor(DEFAULT_MAX_FLOW_SIZE);
	}

	/**
	 * 新建实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午2:59:56
	 * @param maxFlowSize
	 *            允许最大的限流量
	 * @return
	 */
	public static FlowMonitor newInstance(int maxFlowSize) {
		return new FlowMonitor(maxFlowSize);
	}

	/**
	 * 线程进入，并发控制<br>
	 * 如果线程进入成功，要配合{@linkplain FlowMonitor#release()}使用
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午11:09:59
	 * @return 如果通过，则返回true;反之返回false;
	 */
	public boolean entry() {
		if (maxFlowSize <= 0) {
			// 如果最大限流配置数小于等于0，表示不限流
			return true;
		}

		if (maxFlowSize <= runningAtomic.get()) {
			// 如果运行中的线程数已经大于当前最大限制
			loseAtomic.incrementAndGet();
			return false;
		}

		// 通过，并发记录数+1
		int size = runningAtomic.incrementAndGet();
		if (size > maxFlowSize) {
			// 并发请求下记录数+1之后超过最大记录数，则需要-1处理
			runningAtomic.decrementAndGet();

			return false;
		}

		passAtomic.incrementAndGet();
		return true;
	}

	/**
	 * 执行后，释放并发数-1
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午11:09:33
	 * @return
	 */
	public int release() {
		return runningAtomic.decrementAndGet();
	}
}
