package com.flyingh.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {
	public static void main(String[] args) throws InterruptedException {
		final Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
		final Thread thread1 = new Thread(() -> {
			try {
				final List<String> list = exchanger.exchange(Arrays.asList("a", "b"));
				System.out.format("%s-->%s:%s%n", Thread.currentThread().getName(), list.getClass().getCanonicalName(), list);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}, "A");
		final Thread thread2 = new Thread(() -> {
			try {
				final List<String> list = exchanger.exchange(new ArrayList<>(Arrays.asList("c", "d", "e")));
				System.out.format("%s-->%s:%s%n", Thread.currentThread().getName(), list.getClass().getCanonicalName(), list);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}, "B");
		thread1.start();
		thread2.start();
	}
}
