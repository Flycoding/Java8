package com.flyingh.demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinPoolDemo {
	public static void main(String[] args) {
		System.out.println(new ForkJoinPool().invoke(new SumTask(0, 10001)));
	}

	static class SumTask extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 3292916256050545456L;
		private final int low;// inclusive
		private final int high;// exclusive

		public SumTask(int low, int high) {
			super();
			this.low = low;
			this.high = high;
		}

		@Override
		protected Integer compute() {
			if (high - low <= 5000) {
				return IntStream.range(low, high).sum();
			}
			final int middle = low + high >>> 1;
			final SumTask leftTask = new SumTask(low, middle);
			final SumTask rightTask = new SumTask(middle, high);
			leftTask.fork();
			return rightTask.compute() + leftTask.join();
		}
	}
}
