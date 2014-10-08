package com.flyingh.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class PCDemo2 {
	private static final int COUNT = 10;

	public static void main(String[] args) {
		final BlockingQueue<Integer> queue = new SynchronousQueue<>();
		final ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Producer(queue));
		executorService.execute(new Consumer(queue));
		executorService.shutdown();
	}

	static class Producer implements Runnable {
		private final BlockingQueue<Integer> queue;

		public Producer(BlockingQueue<Integer> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			IntStream.range(0, COUNT).forEach(i -> {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
					final int value = ThreadLocalRandom.current().nextInt(-100, 101);
					System.out.format("put the data %d%n", value);
					queue.put(value);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	static class Consumer implements Runnable {
		private final BlockingQueue<Integer> queue;

		public Consumer(BlockingQueue<Integer> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			IntStream.range(0, COUNT).forEach(i -> {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
					System.out.format("take the data %d%n", queue.take());
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}
