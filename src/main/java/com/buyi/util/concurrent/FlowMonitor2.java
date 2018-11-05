package com.buyi.util.concurrent;

import java.util.concurrent.Semaphore;

/**
 * <p>
 * 限流监控器
 * </p>
 * 该限流使用{@link Semaphore},如果使用{@linkplain Semaphore#acquire()}
 * 该限流，在无可用的许可场景下，将一直阻塞等待，在实际并发场景中考虑的是fastFail，所以，适用性并不好强。
 * 如果使用{@linkplain Semaphore#tryAcquire()} 从信号量尝试获取一个许可，如果无可用，直接返回false，不会造成阻塞。
 * 
 * <p>
 * 该限流使用{@linkplain Semaphore#acquire()}方式
 * </p>
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017上午11:05:33
 */
public class FlowMonitor2 {

	private static final int DEFAULT_MAX_FLOW_SIZE = 100;

	private final Semaphore permit;

	private FlowMonitor2(int maxFlowSize) {
		// 构建Semaphore参数fair true 线程获取许可的顺序是有序的
		this.permit = new Semaphore(maxFlowSize, true);
	}

	/**
	 * 新建实例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午2:59:32
	 * @return
	 */
	public static FlowMonitor2 newInstance() {
		return new FlowMonitor2(DEFAULT_MAX_FLOW_SIZE);
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
	public static FlowMonitor2 newInstance(int maxFlowSize) {
		return new FlowMonitor2(maxFlowSize);
	}

	/**
	 * 线程进入，并发控制<br>
	 * 如果线程进入成功，要配合{@linkplain FlowMonitor2#release()}使用。
	 * 
	 * 如果考虑获取许可的时间限制，可以使用{@linkplain Semaphore#tryAcquire(long, java.util.concurrent.TimeUnit)}
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午11:09:59
	 */
	public boolean entry() {
		return permit.tryAcquire();
	}

	/**
	 * 线程进入<br>
	 * 如果没有获取到可用的许可，会导致阻塞，慎用。要配合{@linkplain FlowMonitor2#release()}使用
	 * 
	 * @author buyi
	 * @throws InterruptedException
	 * @since 1.0.0
	 * @date 2017下午3:25:01
	 */
	public void entryBlock() {
		try {
			permit.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行后，释放并发数-1
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午11:09:33
	 * @return
	 */
	public void release() {
		permit.release();
	}

	/**
	 * 获取允许的信号量
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:29:41
	 * @return
	 */
	public int availablePermits() {
		return permit.availablePermits();
	}

	public Semaphore getPermit() {
		return permit;
	}

	public static void main(String[] args) {
		final FlowMonitor2 flowMonitor = FlowMonitor2.newInstance(10);

		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean entry = flowMonitor.entry();
					System.out.println(Thread.currentThread().getName() + "获得许可:" + entry);

					if (entry) {
						flowMonitor.release();
					}
				}
			}).start();
		}
	}
}
