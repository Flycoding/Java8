package com.flyingh.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class Demo {

	private static final String FILE_NAME = "dictionary.txt";
	private static final int MIN_REPEAT_NUMBER = 8;

	@Test
	public void test8() throws IOException {
		final Map<String, List<String>> map = new HashMap<>();
		try (final BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));) {
			String str = null;
			while ((str = br.readLine()) != null) {
				final String orderedStr = order(str);
				List<String> list = map.get(orderedStr);
				if (list == null) {
					map.put(orderedStr, list = new ArrayList<>());
				}
				list.add(str);
			}
		}
		for (final Map.Entry<String, List<String>> me : map.entrySet()) {
			final List<String> list = me.getValue();
			if (list.size() >= MIN_REPEAT_NUMBER) {
				System.out.println(list.size() + ":" + me.getKey() + "-->" + list);
			}
		}
	}

	private String order(String str) {
		final char[] array = str.toCharArray();
		Arrays.sort(array);
		return new String(array);
	}

	@Test
	public void test7() {
		final Map<String, String> map = new HashMap<>();
		map.put("A", "B");
		map.put("B", "C");
		map.put("C", null);
		// map.keySet().removeAll(new HashSet<>(map.values()));
		map.values().removeAll(Collections.singleton("C"));
		System.out.println(map);
	}

	@Test
	public void test6() {
		Arrays.asList("张三", "李四", "王五", "赵六", "孙七", "钱八").stream().sorted(Collator.getInstance(Locale.CHINA)).forEach(System.out::println);
	}

	@Test
	public void test5() {
		final PriorityQueue<String> priorityQueue = new PriorityQueue<>(Arrays.asList("c", "a", "b"));
		final ArrayList<String> list = new ArrayList<>();
		System.out.println(priorityQueue);
		while (!priorityQueue.isEmpty()) {
			list.add(priorityQueue.remove());
		}
		list.forEach(System.out::print);
	}

	@Test
	public void test4() {
		final List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
		System.out.println(list);
		list.subList(1, 3).subList(0, 1).clear();
		System.out.println(list);
	}

	@Test
	public void test3() {
		final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		swap(list, 0, list.size() - 1);
		System.out.println(list);
	}

	private void swap(List<Integer> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	@Test
	public void test2() {
		final int[] ints = { 1, 2, 3, 4, 1, 3, 5, 3, 2, 7, 8, 7, 6, 7, 5 };
		final HashSet<Integer> set = new HashSet<>();
		for (final int i : ints) {
			if (!set.add(i)) {
				set.remove(i);
			}
		}
		System.out.println(set);
	}

	@Test
	public void test() {
		final List<Employee> list = Arrays.asList(new Employee(1, "A", 88), new Employee(2, "B", 99), new Employee(3, "C", 100));
		System.out.println(list.stream().map(Object::toString).collect(Collectors.joining(",")));
		System.out.println(list.stream().collect(Collectors.summingDouble(Employee::getSalary)));
		Arrays.asList("a", "b", "a", "c", "d", "e", "d", "f", "b", "e").stream().collect(Collectors.toSet()).forEach(System.out::print);
		System.out.println();
		list.stream().map(Employee::getSalary).collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);
		list.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);
	}

}
