package com.flyingh.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class Demo {

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
	}

}
