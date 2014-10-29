package com.flyingh.i18n.demo;

import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.Locale.LanguageRange;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class I18nDemo {

	@Test
	public void test10() {
		System.out.println(Locale.getDefault());
		final Locale locale = Locale.US;
		final ResourceBundle bundle = ResourceBundle.getBundle("book", locale);
		final MessageFormat messageFormat = new MessageFormat(bundle.getString("pattern"), locale);
		messageFormat.setFormats(new Format[] { null,
				newChoiceFormat(new double[] { 0, 1, 2 }, new String[] { bundle.getString("noBooks"), bundle.getString("oneBook"), bundle.getString("multipleBooks") }),
				NumberFormat.getInstance() });
		final String name = bundle.getString("name");
		System.out.println(format(messageFormat, name, 0));
		System.out.println(format(messageFormat, name, 1));
		System.out.println(format(messageFormat, name, 2, 2));
		System.out.println(format(messageFormat, name, 3, 3));
		System.out.println(format(messageFormat, name, 100, 3));
	}

	private ChoiceFormat newChoiceFormat(final double[] limits, final String[] formats) {
		return new ChoiceFormat(limits, formats);
	}

	private String format(MessageFormat messageFormat, Object... args) {
		return messageFormat.format(args);
	}

	@Test
	public void test9() {
		System.out.println(new SimpleDateFormat("E", Locale.US).format(new Date()));
		final DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
		System.out.println(Stream.of(symbols.getShortWeekdays()).collect(Collectors.toList()));
		symbols.setShortWeekdays(new String[] { "", "1", "2", "3", "4", "5", "6", "7" });
		System.out.println(new SimpleDateFormat("E", symbols).format(new Date()));
	}

	@Test
	public void test8() throws Exception {
		System.out.println(Locale.getDefault());
		System.out.println(Locale.getDefault(Category.DISPLAY));
		System.out.println(Locale.getDefault(Category.FORMAT));
		final Date date = new Date();
		System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.forLanguageTag("en_US")).format(date));
		System.out.format("%tF %<tT%n", date);
		System.out.println(format("G", date));
		System.out.println("************************************");
		System.out.println(format("G", date, Locale.US));
		System.out.println(format("d", date));
		System.out.println(format("D", date));
		System.out.println(format("E", date));
		System.out.println(format("F", date));
		System.out.println(format("w", date));
		System.out.println(format("W", date));
		System.out.println(format("a", date));
		System.out.println(format("z", date));
		System.out.println(format("h", date));
		System.out.println(format("k", date));
		System.out.println(format("K", date));
		System.out.println(format("yyyy哈哈MM", date));
		System.out.println(format("yy", date));
		System.out.println(format("EEE", date, Locale.US));
		System.out.println(format("EEEE", date, Locale.US));
	}

	private String format(String pattern, Date date, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault(Category.FORMAT);
		}
		return new SimpleDateFormat(pattern, locale).format(date);
	}

	private String format(String pattern, final Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	@Test
	public void test7() throws Exception {
		System.out.println(Currency.getInstance(Locale.CHINA).getSymbol(Locale.US));
		System.out.println(Currency.getInstance(Locale.CHINA).getSymbol(Locale.PRC));
	}

	@Test
	public void test6() throws Exception {
		System.out.println(Currency.getInstance(new Locale.Builder().setLanguage("en").setRegion("US").build()).getSymbol());
		System.out.println(Currency.getInstance(Locale.US).getSymbol());
		final Currency currency = Currency.getInstance(Locale.CHINA);
		System.out.println(currency.getDisplayName());
		System.out.println(currency.getDisplayName(Locale.US));
		System.out.println(currency.getSymbol());
		System.out.println(currency.getSymbol(Locale.CHINA));
		System.out.println(Currency.getInstance(Locale.JAPAN).getSymbol(Locale.JAPAN));
	}

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
