package com.flyingh.demo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class PCDemo3 {
	public static void main(String[] args) {
		final Data data = new Data(3);
		final ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Producer(data));
		executorService.execute(new Consumer(data));
		executorService.shutdown();
	}

	static class Producer implements Runnable {
		private final Data data;

		public Producer(Data data) {
			this.data = data;
		}

		@Override
		public void run() {
			IntStream.range(0, 10).forEach(i -> {
				try {
					data.put(ThreadLocalRandom.current().nextInt(10));
					TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 50));
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	static class Consumer implements Runnable {
		private final Data data;

		public Consumer(Data data) {
			this.data = data;
		}

		@Override
		public void run() {
			IntStream.range(0, 10).forEach(i -> {
				try {
					data.take();
					TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 500));
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	static class Data {
		private final int maxSize;
		private final Deque<Integer> deque = new ArrayDeque<Integer>();
		private final Lock lock = new ReentrantLock();
		private final Condition condition = lock.newCondition();

		public Data() {
			this(10);
		}

		public Data(int maxSize) {
			super();
			this.maxSize = maxSize;
		}

		public void put(int value) throws InterruptedException {
			lock.lock();
			System.err.println(deque.size());
			try {
				while (isFull()) {
					condition.await();
				}
				deque.add(value);
				System.out.format("%s put:%d%n", Thread.currentThread().getName(), value);
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}

		public Integer take() throws InterruptedException {
			lock.lock();
			System.err.println(deque.size());
			try {
				while (isEmpty()) {
					condition.await();
				}
				final Integer result = deque.removeFirst();
				System.out.format("%s take:%d%n", Thread.currentThread().getName(), result);
				condition.signalAll();
				return result;
			} finally {
				lock.unlock();
			}
		}

		private boolean isEmpty() {
			lock.lock();
			try {
				return deque.isEmpty();
			} finally {
				lock.unlock();
			}
		}

		private boolean isFull() {
			lock.lock();
			try {
				return deque.size() == maxSize;
			} finally {
				lock.unlock();
			}
		}

	}
}
