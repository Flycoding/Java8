package com.flyingh.demo;

import java.util.Random;

public class StartDemo {
	public static void main(String[] args) {

		final MR target = new MR();
		final Thread thread = new Thread(target);
		final Thread thread2 = new Thread(target);
		thread.start();
		thread2.start();
	}

	static class MR implements Runnable {
		private int num;

		@Override
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(500));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(++num);
		}
	}
}
