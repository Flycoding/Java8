package com.flyingh.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class CountDownLatchDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final int[] array = { 3, 1, 5, 2, 8, 7, 6, 4, 9 };
		final CountDownLatch latch = new CountDownLatch(2);
		final ExecutorService executorService = Executors.newFixedThreadPool(2);
		final List<Future<Integer>> futures = executorService.invokeAll(Arrays.asList(new MaxCallable(array, 0, array.length / 2, latch),
				new MaxCallable(array, array.length / 2, array.length, latch)));
		latch.await();
		System.out.println(Math.max(futures.get(0).get(), futures.get(1).get()));
		executorService.shutdown();
	}

	static class MaxCallable implements Callable<Integer> {
		private final int[] array;
		private final int from;
		private final int to;
		private final CountDownLatch latch;

		public MaxCallable(int[] array, int from, int to, CountDownLatch latch) {
			super();
			this.array = array;
			this.from = from;
			this.to = to;
			this.latch = latch;
		}

		@Override
		public Integer call() throws Exception {
			Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
			final int result = Arrays.stream(array, from, to).max().getAsInt();
			latch.countDown();
			System.out.println("remaining " + latch.getCount());
			return result;
		}

	}
}
