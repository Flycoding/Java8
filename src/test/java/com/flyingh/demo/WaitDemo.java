package com.flyingh.demo;

public class WaitDemo {
	public static void main(String[] args) {
		new WaitDemo().test();
	}

	private void test() {
		final Flag flag = new Flag();
		new Thread(() -> doWaiting(flag)).start();
		new Thread(() -> doNotifying(flag)).start();
	}

	private synchronized void doNotifying(Flag flag) {
		try {
			Thread.sleep(3000);
			flag.setFlag(true);
			notifyAll();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void doWaiting(final Flag flag) {
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
	}

	static class Flag {
		boolean flag;

		public synchronized boolean isFlag() {
			return flag;
		}

		public synchronized void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
}
