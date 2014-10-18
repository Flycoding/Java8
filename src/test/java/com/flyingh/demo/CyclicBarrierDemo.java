package com.flyingh.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class CyclicBarrierDemo {
	private static final int COUNT = 5;

	public static void main(String[] args) throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(COUNT);
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT);
		final List<Future<Integer>> futures = executorService.invokeAll(Arrays.asList(new Task(0, 2001, cyclicBarrier), new Task(2001, 4001,
				cyclicBarrier), new Task(4001, 6001, cyclicBarrier), new Task(6001, 8001, cyclicBarrier), new Task(8001, 10001, cyclicBarrier)));
		final int sum = futures.stream().mapToInt(f -> {
			try {
				return f.get();
			} catch (final Exception e) {
				e.printStackTrace();
			}
			return 0;
		}).sum();
		System.out.format("sum=%d%n", sum);
		executorService.shutdown();
	}

	static class Task implements Callable<Integer> {
		private final int startInclusive;
		private final int endExclusive;
		private final CyclicBarrier cyclicBarrier;

		public Task(int startInclusive, int endExclusive, CyclicBarrier cyclicBarrier) {
			this.startInclusive = startInclusive;
			this.endExclusive = endExclusive;
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public Integer call() throws Exception {
			final int result = IntStream.range(startInclusive, endExclusive).sum();
			System.out.format("%s:result:%08d%n", Thread.currentThread().getName(), result);
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName());
			return result;
		}

	}
}
