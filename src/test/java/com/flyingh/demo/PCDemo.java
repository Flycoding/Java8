package com.flyingh.demo;

import java.util.Random;
import java.util.stream.IntStream;

public class PCDemo {
	private static final int COUNT = 10;

	public static void main(String[] args) {
		final Data data = new Data();
		new Thread(new Producer(data)).start();
		new Thread(new Consumer(data)).start();
	}

	static class Producer implements Runnable {
		private final Data data;

		public Producer(Data data) {
			super();
			this.data = data;
		}

		@Override
		public void run() {
			IntStream.range(0, COUNT).forEach(i -> data.put(Math.round(Math.random() * 100)));
		}
	}

	static class Consumer implements Runnable {
		private final Data data;

		public Consumer(Data data) {
			super();
			this.data = data;
		}

		@Override
		public void run() {
			IntStream.range(0, COUNT).forEach(i -> data.take());
		}

	}

	static class Data {
		private boolean empty = true;
		private Object value;

		synchronized void put(long value) {
			while (!empty) {
				System.out.println("waiting to put...");
				try {
					Thread.sleep(new Random().nextInt(5000));
					wait();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.value = value;
			System.out.println("put the data :" + value);
			empty = false;
			notifyAll();
		}

		synchronized Object take() {
			while (empty) {
				System.out.println("waiting to take...");
				try {
					Thread.sleep(new Random().nextInt(3000));
					wait();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			final Object result = value;
			System.out.println("take the data :" + value);
			value = null;
			empty = true;
			notifyAll();
			return result;
		}
	}
}
