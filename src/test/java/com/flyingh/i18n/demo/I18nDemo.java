package com.flyingh.i18n.demo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.RuleBasedCollator;
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

import org.junit.Assert;
import org.junit.Test;

public class I18nDemo {
	@Test
	public void test28() throws Exception {
		System.out.println(IDN.toASCII("清华大学"));
		System.out.println(IDN.toUnicode("xn--xkry9kk1bz66a"));
	}

	@Test
	public void test27() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		final String str = "A" + "\u00ea" + "\u00f1" + "\u00fc" + "C";
		System.out.println(str);
		final byte[] bytes = str.getBytes("UTF8");
		for (final byte b : bytes) {
			System.out.format("0x%02x%n", b);
		}
	}

	@Test
	public void test26() throws Exception {
		System.out.println(new OutputStreamWriter(new ByteArrayOutputStream()).getEncoding());
		System.out.println(System.getProperty("file.encoding"));
	}

	@Test
	public void test25() throws Exception {
		final String text = "She said, \"Hello there,\" and then " + "went on down the street. When she stopped "
				+ "to look at the fur coats in a shop + " + "window, her dog growled. \"Sorry Jake,\" " + "she said. \"I didn't know you would take "
				+ "it personally.\"";
		final BreakIterator lineInstance = BreakIterator.getLineInstance();
		lineInstance.setText(text);
		final int MAX_LENGTH = 30;
		int lineLength = 0;
		for (int start = lineInstance.first(), end = lineInstance.next(); end != BreakIterator.DONE; start = end, end = lineInstance.next()) {
			if ((lineLength += end - start) >= MAX_LENGTH) {
				System.out.println();
				lineLength = end - start;
			}
			System.out.print(text.substring(start, end));
		}

	}

	@Test
	public void test24() throws Exception {
		final BreakIterator wordInstance = BreakIterator.getWordInstance();
		wordInstance.setText("hello world");
		Assert.assertTrue(wordInstance.next() == 5);
		Assert.assertTrue(wordInstance.first() == 0);
		Assert.assertEquals(2, "𐐀".length());
	}

	@Test
	public void test23() throws Exception {
		final String str = "He's vanished!  What will we do?  It's up to us.";
		final BreakIterator sentenceInstance = BreakIterator.getSentenceInstance();
		sentenceInstance.setText(str);
		int end = sentenceInstance.first();
		final StringBuilder builder = new StringBuilder(new String(new char[str.length() + 1]));
		while (end != BreakIterator.DONE) {
			builder.setCharAt(end, '^');
			end = sentenceInstance.next();
		}
		System.out.println(str);
		System.out.println(builder);
	}

	@Test
	public void test22() throws Exception {
		final String str = "She stopped.  She said, \"Hello there,\" and then";
		final BreakIterator wordInstance = BreakIterator.getWordInstance();
		wordInstance.setText(str);
		for (int start = wordInstance.first(), end = wordInstance.next(); end != BreakIterator.DONE; start = end, end = wordInstance.next()) {
			final String substring = str.substring(start, end);
			if (Character.isLetterOrDigit(substring.charAt(0))) {
				System.out.println(substring);
			}
		}
	}

	@Test
	public void test21() throws Exception {
		final String str = "She stopped.  She said, \"Hello there,\" and then";
		final BreakIterator wordInstance = BreakIterator.getWordInstance();
		wordInstance.setText(str);
		int end = wordInstance.first();
		final StringBuilder builder = new StringBuilder(new String(new char[str.length() + 1]));
		while (end != BreakIterator.DONE) {
			builder.setCharAt(end, '^');
			end = wordInstance.next();
		}
		System.out.println(str);
		System.out.println(builder);
	}

	@Test
	public void test20() {
		final String house = "\u0628" + "\u064e" + "\u064a" + "\u0652" + "\u067a" + "\u064f";
		final BreakIterator characterInstance = BreakIterator.getCharacterInstance(Locale.forLanguageTag("ar_SA"));
		characterInstance.setText(house);
		int end = characterInstance.first();
		while (end != BreakIterator.DONE) {
			System.out.println(end);
			end = characterInstance.next();
		}
	}

	@Test
	public void test19() {
		System.out.println(String.valueOf('\u00fc'));
	}

	@Test
	public void test18() {
		final String str = "Hello world! Today is Sunday!!!";
		final BreakIterator wordInstance = BreakIterator.getWordInstance();
		wordInstance.setText(str);
		for (int start = wordInstance.first(), end = wordInstance.next(); end != BreakIterator.DONE; start = end, end = wordInstance.next()) {
			System.out.format("start:%d,end:%d%n", start, end);
			System.out.println(str.substring(start, end));
		}
	}

	@Test
	public void test17() {
		Stream.of(BreakIterator.getAvailableLocales()).forEach(System.out::println);
	}

	@Test
	public void test16() {
		final String str = "a";
		System.out.println(str == str.substring(0));
	}

	@Test
	public void test15() {
		final char[] value = new char[2];
		System.out.println(new String(value, 0, Character.toChars(0x10400, value, 0)));
	}

	@Test
	public void test14() throws Exception {
		System.out.println(String.valueOf(0x4e00));
		System.out.println(Character.charCount(0x4e00));
		System.out.println(String.valueOf((char) 0x4e00));
		System.out.println(new String(Character.toChars(0x4e00)));
	}

	@Test
	public void test13() throws Exception {
		final String str = new String(Character.toChars(0x10400));
		System.out.println(str);
		System.out.println(str.length());
		System.out.println(new String(Character.toChars(0x4E00)));
		System.out.println(new String(Character.toChars(0x9FA5)));
		final String str2 = new String(Character.toChars(0x10FFFF));
		System.out.println(str2);
		System.out.println(str2.length());
	}

	@Test
	public void test12() throws Exception {
		System.out.println(Character.toChars(0x10400));
		System.out.println(Arrays.toString(Character.toChars(0x10400)));
		System.out.println(Character.codePointAt("A", 0));
		System.out.println("a".codePointAt(0));
		System.out.println(Character.charCount(0x10FF00));
		System.out.println(Character.charCount(0x10400));
		System.out.println(Character.charCount(97));
		System.out.println("abcde".offsetByCodePoints(1, 2));
	}

	@Test
	public void test11() throws ParseException {
		Stream.of("1", "0", "a", "b", "c", "d", "e", "6", "5", "7").sorted(new RuleBasedCollator("<e<d<b<a<c")).forEach(System.out::println);
	}

	@Test
	public void test10() {
		System.out.println(Locale.getDefault());
		final Locale locale = Locale.US;
		final ResourceBundle bundle = ResourceBundle.getBundle("book", locale);
		final MessageFormat messageFormat = new MessageFormat(bundle.getString("pattern"), locale);
		messageFormat.setFormats(new Format[] {
				null,
				newChoiceFormat(new double[] { 0, 1, 2 },
						new String[] { bundle.getString("noBooks"), bundle.getString("oneBook"), bundle.getString("multipleBooks") }),
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
		Locale.filter(LanguageRange.parse(ranges), Stream.of(tags).map(str -> Locale.forLanguageTag(str)).collect(Collectors.toList())).forEach(
				System.out::println);
		System.out.println("****************");
		Locale.filterTags(LanguageRange.parse(ranges), Arrays.asList(tags)).forEach(System.out::println);
		System.out.println("#########################");
		System.out.println(Locale.lookup(LanguageRange.parse(ranges),
				Stream.of(tags).map(str -> Locale.forLanguageTag(str)).collect(Collectors.toList())));
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(Locale.lookupTag(LanguageRange.parse(ranges), Stream.of(tags).collect(Collectors.toList())));
	}

	@Test
	public void test3() throws Exception {
		LanguageRange.parse("zh-CN;q=1.0,en-US;q=0.8,en-GB;q=0.5,jp-JP;q=0.0").stream().map(lr -> lr.getRange() + "-----" + lr.getWeight())
		.forEach(System.out::println);
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
