package com.flyingh.i18n.demo;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class I18nDemo {

	@Test
	public void test5() throws Exception {
		System.out.println(Locale.getDefault());
	}

	@Test
	public void test4() throws Exception {
		final String ranges = "zh-CN;q=1.0,en-GB;q=0.5,en-US;q=0.8,zh-TW;q=0.0";
		final String[] tags = { "en-GB", "zh-CN", "ja-JP", "zh-TW", "en-US" };
		Locale.filter(LanguageRange.parse(ranges), Stream.of(tags).map(str -> Locale.forLanguageTag(str)).collect(Collectors.toList())).forEach(System.out::println);
		System.out.println("****************");
		Locale.filterTags(LanguageRange.parse(ranges), Arrays.asList(tags)).forEach(System.out::println);
		System.out.println("#########################");
		System.out.println(Locale.lookup(LanguageRange.parse(ranges), Stream.of(tags).map(str -> Locale.forLanguageTag(str)).collect(Collectors.toList())));
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(Locale.lookupTag(LanguageRange.parse(ranges), Stream.of(tags).collect(Collectors.toList())));
	}

	@Test
	public void test3() throws Exception {
		LanguageRange.parse("zh-CN;q=1.0,en-US;q=0.8,en-GB;q=0.5,jp-JP;q=0.0").stream().map(lr -> lr.getRange() + "-----" + lr.getWeight()).forEach(System.out::println);
	}

	@Test
	public void test2() {
		final Locale locale = Locale.forLanguageTag("zh-CN");
		System.out.println(locale);
		System.out.println(locale.getDisplayName());
	}

	@Test
	public void test() {
		Stream.of(DateFormat.getAvailableLocales()).map(l -> l.getDisplayName()).sorted().forEach(System.out::println);
	}
}
