package com.flyingh.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegExDemo {

	@Test
	public void test2() {
		System.out.println("2".matches("[0-3&&[^3]]"));
	}

	@Test
	public void test() {
		// final Matcher matcher = Pattern.compile("a?").matcher("b");
		// final Matcher matcher = Pattern.compile("a{3}").matcher("aaaa");
		// final Matcher matcher = Pattern.compile("a{3,6}").matcher("aaaaaaaaa");
		final Matcher matcher = Pattern.compile(".*?foo").matcher("xfooxxxxxxfoo");
		while (matcher.find()) {
			System.out.format("%s-->start:%d,end:%d%n", matcher.group(), matcher.start(), matcher.end());
		}
	}
}
