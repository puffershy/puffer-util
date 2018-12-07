package com.puffer.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流控制访问速率
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午2:55:49
 */
public class FlowRateMonitor {

	private static final double DEFAULT_MAX_FLOW_SIZE = 100;

	private RateLimiter limiter;

	private FlowRateMonitor() {
		this.limiter = RateLimiter.create(DEFAULT_MAX_FLOW_SIZE);
	}

	private FlowRateMonitor(double flowSize) {
		this.limiter = RateLimiter.create(flowSize);
	}

	public static FlowRateMonitor newInstance() {
		return new FlowRateMonitor();
	}

	public static FlowRateMonitor newInstance(double flowSize) {
		return new FlowRateMonitor(flowSize);
	}

	public boolean entry() {
		return limiter.tryAcquire();
	}

	public boolean entry(long timeout) {
		return limiter.tryAcquire(timeout, TimeUnit.MICROSECONDS);
	}

	public double entryBlock() {
		return limiter.acquire();
	}

	public void release() {
	}

	public RateLimiter getLimiter() {
		return limiter;
	}

	public void setLimiter(RateLimiter limiter) {
		this.limiter = limiter;
	}

	public static void main(String[] args) throws InterruptedException {
		final FlowRateMonitor monitor = FlowRateMonitor.newInstance(5);

		ExecutorService exe = Executors.newFixedThreadPool(10);
		// System.out.println(System.currentTimeMillis());
		Thread.sleep(1000);

		for (int i = 0; i < 100; i++) {

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					boolean entry = monitor.entry();
					if (entry)
						System.out.println(">>" + Thread.currentThread().getName() + "\t" + entry + "\t"
								+ System.currentTimeMillis());

					// double entryBlock = monitor.entryBlock();
					//
					// System.out.println(">>" +
					// Thread.currentThread().getName() + "\t" + entryBlock +
					// "\t"
					// + System.currentTimeMillis());
					// try {
					// Thread.currentThread().sleep(500);
					// } catch (InterruptedException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}

			});

			exe.execute(thread);
		}

		exe.shutdown();

		// while (true) {
		// if (exe.isTerminated()) {
		// break;
		// }
		//
		// Thread.sleep(100);
		// }
	}

}
