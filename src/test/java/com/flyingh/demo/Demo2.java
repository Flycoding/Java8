package com.flyingh.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.junit.Test;

public class Demo2 {

	@Test
	public void test9() {
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.home"));
		System.out.println("##########################################");
		System.out.println(System.getenv("JAVA_HOME"));
		System.out.println(System.getenv("Path"));
		System.out.println("******************************************");
		System.getenv().forEach((k, v) -> System.out.println(k + "=" + v));
	}

	@Test
	public void test8() {
		System.out.println(ThreadLocalRandom.current().nextInt(-99, 100));
	}

	@Test
	public void test7() {
		final Flag flag = new Flag();
		new Thread(() -> {
			synchronized (this) {
				while (!flag.isFlag()) {
					try {
						System.out.println("waiting...");
						wait();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("here");
			}
		}).start();
		new Thread(() -> {
			try {
				Thread.sleep(100);
				flag.setFlag(true);
				notifyAll();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	class Flag {
		boolean flag;

		public synchronized boolean isFlag() {
			return flag;
		}

		public synchronized void setFlag(boolean flag) {
			this.flag = flag;
		}
	}

	@Test
	public void test6() {
		final MR target = new MR();
		final Thread thread = new Thread(target);
		final Thread thread2 = new Thread(target);
		thread.start();
		thread2.start();
	}

	class MR implements Runnable {
		private int num;

		@Override
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(5000));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(++num);
		}
	}

	@Test
	public void test5() {
		final int count = 1;
		new Thread(() -> System.out.println(count)).start();
	}

	@Test
	public void test4() throws InterruptedException {
		final Thread thread = new Thread(() -> doRun());
		thread.start();
		final long startTime = System.currentTimeMillis();
		System.out.println("Starting:");
		while (thread.isAlive()) {
			System.out.println("waiting...");
			thread.join(1000);
			if (System.currentTimeMillis() - startTime >= 5000 && thread.isAlive()) {
				System.out.println("Tired of waiting...");
				thread.interrupt();
				thread.join();
			}
		}
		System.out.println("finally");
	}

	private void doRun() {
		final List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		try {
			for (final String str : list) {
				Thread.sleep(2000);
				System.out.println(str);
			}
		} catch (final Exception e) {
			System.out.println("not done.");
		}
	}

	@Test
	public void test3() {
		System.out.println(Thread.currentThread().getName());
		Stream.of("a", "b", "c", "d", "e").forEach(str -> {
			try {
				Thread.sleep(3000);
			} catch (final Exception e) {
				return;
			}
			System.out.println(str);
		});
	}

	@Test
	public void test2() {
		new Thread(() -> System.out.println("hahaha!!!")).start();
	}

	@Test
	public void test() {
		new Thread() {
			@Override
			public void run() {
				System.out.println("hello world!!!");
			}
		}.start();
	}
}
