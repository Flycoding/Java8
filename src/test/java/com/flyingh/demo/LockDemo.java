package com.flyingh.demo;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	public static void main(String[] args) {
		final Person asker = new Person("Teacher");
		final Person answerer = new Person("Student");
		new Thread(new PersonRunnable(asker, answerer)).start();
		new Thread(new PersonRunnable(answerer, asker)).start();
	}

	static class PersonRunnable implements Runnable {
		private final Person asker;
		private final Person answerer;

		public PersonRunnable(Person asker, Person answerer) {
			super();
			this.asker = asker;
			this.answerer = answerer;
		}

		@Override
		public void run() {
			final Random random = new Random();
			for (;;) {
				try {
					Thread.sleep(random.nextInt(100));
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
				asker.ask(answerer);
			}
		}

	}

	static class Person {
		private final String name;
		private final Lock lock = new ReentrantLock();

		public Person(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void ask(Person answerer) {
			if (lockSuccess(answerer)) {
				try {
					System.out.format("ask:%s:%s%n", name, answerer.name);
					answerer.answer(this);
				} finally {
					lock.unlock();
					answerer.lock.unlock();
				}
			} else {
				System.out.format("fail:%s:%s%n", name, answerer.name);
			}
		}

		public void answer(Person person) {
			System.out.format("answer:%s:%s%n", name, person.name);
		}

		private boolean lockSuccess(Person answerer) {
			boolean askLock = false;
			boolean answerLock = false;
			try {
				askLock = lock.tryLock();
				answerLock = answerer.lock.tryLock();
				return askLock && answerLock;
			} finally {
				if (!(askLock && answerLock)) {
					if (askLock) {
						lock.unlock();
					}
					if (answerLock) {
						answerer.lock.unlock();
					}
				}
			}
		}
	}
}
