package com.flyingh.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class Demo {

	private static final String SCANNER_FILE = "scanner.txt";
	private static final String FILE_NAME = "dictionary.txt";
	private static final int MIN_REPEAT_NUMBER = 8;

	@Test
	public void test57() {
		final byte[] array = ByteBuffer.allocate(4).putInt(5).array();
		System.out.println(Arrays.toString(array));
		System.out.println(new BigInteger(1, array).intValue());
	}

	@Test
	public void test56() throws IOException {
		FileSystems.getDefault().getFileStores().forEach(System.out::println);
		System.out.println("########################");
		FileSystems.getDefault().getRootDirectories().forEach(System.out::println);
		System.out.println("*********************");
		System.out.println(Files.getFileStore(Paths.get("C:\\Windows")));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Stream.of(File.listRoots()).forEach(System.out::println);
	}

	@Test
	public void test55() {
		System.out.println(File.separator);
		System.out.println(FileSystems.getDefault().getSeparator());
	}

	@Test
	public void test54() throws IOException {
		System.out.println(Files.probeContentType(Paths.get("Hello.mp3")));
	}

	@Test
	public void test53() {
		FileSystems.getDefault().supportedFileAttributeViews().forEach(System.out::println);
	}

	@Test
	public void test52() {
		final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{java,class}");
		System.out.println(pathMatcher.matches(Paths.get("HelloWorld.java")));
		System.out.println(pathMatcher.matches(Paths.get("HelloWorld.jar")));
		System.out.println(pathMatcher.matches(Paths.get("HelloWorld.class")));
		System.out.println(pathMatcher.matches(Paths.get("HelloWorld.html")));
	}

	@Test
	public void test51() throws IOException {
		Files.walk(Paths.get("C:\\Program Files"), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS).forEach(System.out::println);
	}

	@Test
	public void test50() throws IOException {
		Files.walkFileTree(Paths.get("C:\\"), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println(dir + " begin");
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println(file + "-->" + attrs.lastModifiedTime());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				System.err.println(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				System.out.println(dir + " end");
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Test
	public void test49() throws IOException {
		FileSystems.getDefault().getRootDirectories().forEach(System.out::println);
		Files.createDirectory(Paths.get("a"));
		Files.createDirectories(Paths.get("a\\b\\c"));
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("C:\\"))) {
			directoryStream.forEach(System.out::println);
		}
		System.out.println("*************");
		try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("."), "*.{txt,class,xml}*")) {
			directoryStream.forEach(path -> {
				System.out.println(path.normalize());
			});
		}
		System.out.println("#############");
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(System.getProperty("user.dir")),
				path -> Files.isDirectory(path) || path.endsWith("pom.xml"))) {
			directoryStream.forEach(System.out::println);
		}
	}

	@Test
	public void test48() throws IOException {
		try (FileChannel fileChannel = FileChannel.open(Paths.get("info"), StandardOpenOption.READ, StandardOpenOption.WRITE)) {
			final ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
			while (byteBuffer.hasRemaining()) {
				fileChannel.read(byteBuffer);
			}
			final ByteBuffer buffer = ByteBuffer.wrap("haha!!!".getBytes());
			fileChannel.position(0);
			while (buffer.hasRemaining()) {
				fileChannel.write(buffer);
			}
			byteBuffer.rewind();
			while (byteBuffer.hasRemaining()) {
				fileChannel.write(byteBuffer);
			}
			buffer.flip();
			while (buffer.hasRemaining()) {
				fileChannel.write(buffer);
			}
		}
	}

	@Test
	public void test47() throws IOException {
		final Path path = Files.createFile(Paths.get("abc"));
		System.out.println(path.toRealPath());
		Files.delete(path);
		final Path tmpPath = Files.createTempFile(null, ".apk");
		System.out.println(tmpPath);
	}

	@Test
	public void test46() throws IOException {
		try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get(SCANNER_FILE));) {
			final ByteBuffer byteBuffer = ByteBuffer.allocate(10);
			final String fileEncoding = System.getProperty("file.encoding");
			while (byteChannel.read(byteBuffer) > 0) {
				byteBuffer.rewind();
				System.out.println(Charset.forName(fileEncoding).decode(byteBuffer));
				byteBuffer.flip();
			}
		}
		try {
			try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("tmp"), new HashSet<>(Arrays.asList(StandardOpenOption.CREATE, StandardOpenOption.APPEND)),
					PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-rw----")))) {
				byteChannel.write(ByteBuffer.wrap("hello world,hahaha!!!".getBytes()));
			}
		} catch (final Exception e) {
			System.out.println(e.getClass().getName());
			Stream.of(e.getSuppressed()).forEach(System.out::println);
		}
	}

	@Test
	public void test45() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(SCANNER_FILE))))) {
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				System.out.println(str);
			}
		}
		try (PrintWriter printWriter = new PrintWriter(Files.newOutputStream(Paths.get("tmp")))) {
			printWriter.println("hello world");
			printWriter.println("this is a test!");
		}
	}

	@Test
	public void test44() throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(SCANNER_FILE), StandardCharsets.UTF_8)) {
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				System.out.println(str);
			}
		}
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("tmp"), StandardCharsets.UTF_8)) {
			bufferedWriter.write("hello world,this is a demo!");
		}
	}

	@Test
	public void test43() throws IOException {
		Files.write(Paths.get("1.txt"), Files.readAllBytes(Paths.get(SCANNER_FILE)), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.write(Paths.get("2.txt"), Files.readAllLines(Paths.get(SCANNER_FILE)));
	}

	@Test
	public void test42() throws IOException {
		final FileStore fileStore = Files.getFileStore(Paths.get(SCANNER_FILE));
		System.out.println(fileStore.getTotalSpace());
		System.out.println(fileStore.getTotalSpace() - fileStore.getUnallocatedSpace());
		System.out.println(fileStore.getUsableSpace());
		System.out.println(fileStore.getUnallocatedSpace());
	}

	@Test
	public void test41() throws IOException {
		final Path path = Paths.get(SCANNER_FILE);
		final UserDefinedFileAttributeView attributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
		final String name = "text.info";
		attributeView.write(name, Charset.defaultCharset().encode("hello world!!!"));
		final ByteBuffer byteBuffer = ByteBuffer.allocate(attributeView.size(name));
		attributeView.read(name, byteBuffer);
		byteBuffer.flip();
		System.out.println(Charset.defaultCharset().decode(byteBuffer).toString());
	}

	@Test
	public void test40() throws IOException {
		try {
			Files.setPosixFilePermissions(Paths.get(SCANNER_FILE), PosixFilePermissions.fromString("rm-------"));
			final PosixFileAttributes attributes = Files.readAttributes(Paths.get(SCANNER_FILE), PosixFileAttributes.class);
			System.out.println(attributes.owner().getName());
			System.out.println(attributes.group().getName());
			final Set<PosixFilePermission> permissions = attributes.permissions();
			System.out.println(permissions);
			Files.createFile(Paths.get("abc"), PosixFilePermissions.asFileAttribute(permissions));
		} catch (final UnsupportedOperationException e) {
			System.out.println(e.getClass().getName());
		} catch (final Exception e) {
			System.out.println(e.getClass().getName());
		}
	}

	@Test
	public void test39() throws IOException {
		final Path path = Paths.get(SCANNER_FILE);
		DosFileAttributes attributes = Files.readAttributes(path, DosFileAttributes.class);
		System.out.println(attributes.isArchive());
		System.out.println(attributes.isReadOnly());
		System.out.println(attributes.isHidden());
		System.out.println(attributes.isSystem());
		Files.setAttribute(path, "dos:hidden", true);
		attributes = Files.readAttributes(path, DosFileAttributes.class);
		System.out.println(attributes.isHidden());
		Files.setAttribute(Paths.get("C:\\Users\\Administrator\\Desktop\\delall.bat"), "dos:hidden", false);
	}

	@Test
	public void test38() throws IOException {
		final Path path = Paths.get(SCANNER_FILE);
		BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
		System.out.println(attributes.creationTime());
		System.out.println(attributes.lastAccessTime());
		System.out.println(attributes.lastModifiedTime());
		System.out.println(attributes.isDirectory());
		System.out.println(attributes.isRegularFile());
		System.out.println(attributes.isSymbolicLink());
		System.out.println(attributes.isOther());
		System.out.println(attributes.size());
		System.out.println(attributes.fileKey());
		Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
		attributes = Files.readAttributes(path, BasicFileAttributes.class);
		System.out.println(attributes.lastModifiedTime());
	}

	@Test
	public void test37() throws IOException {
		assertTrue(Files.exists(Paths.get(SCANNER_FILE)));
		assertTrue(Files.notExists(Paths.get("abc")));
		assertTrue(Files.isExecutable(Paths.get(SCANNER_FILE)));
		assertTrue(Files.isReadable(Paths.get(SCANNER_FILE)));
		assertTrue(Files.isWritable(Paths.get(SCANNER_FILE)));
		assertTrue(Files.isSameFile(Paths.get(SCANNER_FILE), Paths.get(".\\scanner.txt")));
	}

	@Test
	public void test36() {
		final Path path = Paths.get("C:\\a\\b\\c");
		assertTrue(path.startsWith("C:\\"));
		assertTrue(path.startsWith(Paths.get("C:\\")));
		assertTrue(path.endsWith("c"));
		assertTrue(path.endsWith(Paths.get("c")));
		assertNotEquals(path, Paths.get("C:\\a\\b\\c\\..\\c"));
		assertEquals(path, Paths.get("C:\\a\\b\\c\\..\\c").normalize());
		assertTrue(path.compareTo(Paths.get("C:\\a\\b\\c\\d")) < 0);
	}

	@Test
	public void test35() {
		final Path aPath = Paths.get("a");
		final Path bPath = Paths.get("b");
		System.out.println(aPath.relativize(bPath));
		System.out.println(bPath.relativize(aPath));
		final Path cPath = Paths.get("a\\b\\c");
		System.out.println(aPath.relativize(cPath));
		System.out.println(cPath.relativize(aPath));
	}

	@Test
	public void test34() {
		System.out.println(Paths.get("a").resolve("b"));
		System.out.println(Paths.get("a").resolve("C:\\b"));
	}

	@Test
	public void test33() {
		final Path path = Paths.get("a");
		System.out.println(path.getParent());
		System.out.println(path.getRoot());
		final Path absolutePath = path.toAbsolutePath();
		System.out.println(absolutePath);
		System.out.println(absolutePath.getParent());
		System.out.println(absolutePath.getRoot());
	}

	@Test
	public void test32() {
		System.out.println(Paths.get("C:\\a\\.\\b\\c").normalize());
		System.out.println(Paths.get("C:\\a\\..\\a\\b\\c").normalize());
		System.out.println(Paths.get("C:\\a\\..\\a\\b\\c").toAbsolutePath());
		System.out.println(Paths.get("C:\\a\\b\\c").toUri());
	}

	@Test
	public void test31() {
		final Path path = Paths.get("C:\\a\\b\\c");
		System.out.println(path.toString());
		System.out.println(path.getFileName());
		System.out.println(path.getNameCount());
		System.out.println(path.getName(0));
		System.out.println(path.subpath(0, 2));
		System.out.println(path.getParent());
		System.out.println(path.getRoot());
		System.out.println(Paths.get("d\\e").getRoot());
	}

	@Test
	public void test30() {
		final Path path = Paths.get(System.getProperty("user.home"), "logs", "foo.log").toAbsolutePath();
		System.out.println(path);
		path.forEach(System.out::println);
	}

	@Test
	public void test29() throws IOException, ClassNotFoundException {
		final Object obj = new Date();
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(obj);
			oos.writeObject(obj);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()); ObjectInputStream ois = new ObjectInputStream(bais)) {
				final Object o1 = ois.readObject();
				final Object o2 = ois.readObject();
				assertSame(o1, o2);
			}
		}
	}

	@Test
	public void test28() {
		System.out.printf("%tB%n", Calendar.getInstance());
		System.out.printf("%f,%1$+020.10f%n", Math.PI);
	}

	@Test
	public void test27() throws FileNotFoundException {
		int sum = 0;
		try (Scanner scanner = new Scanner(new FileInputStream(SCANNER_FILE));) {
			while (scanner.hasNext()) {
				if (scanner.hasNextInt()) {
					sum += scanner.nextInt();
				} else {
					scanner.next();
				}
			}
		}
		System.out.println(sum);
	}

	@Test
	public void test26() {
		System.out.println(Locale.getDefault());
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println(scanner.nextInt());// 12,345,678
		}
	}

	@Test
	public void test25() throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(System.out);) {
			osw.write(65);
		}
	}

	@Test
	public void test24() {
		final List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 4, 5));
		final int index = Collections.binarySearch(list, 3);
		if (index < 0) {
			list.add(-index - 1, 3);
		}
		System.out.println(list);
	}

	@Test
	public void test23() {
		final Map<Integer, String> map = new HashMap<>();
		map.put(1, "a");
		map.put(2, "b");
		map.put(3, "a");
		map.values().removeAll(Collections.singleton("a"));
		System.out.println(map);
	}

	@Test
	public void test22() {
		final Queue<Integer> queue = new PriorityQueue<>(Arrays.asList(3, 2, 4, 5, 1));
		queue.forEach(System.out::println);
		System.out.println("*********************");
		while (!queue.isEmpty()) {
			System.out.println(queue.poll());
		}
	}

	@Test
	public void test21() {
		final List<Student> students = Arrays.<Student> asList(new Student(1, "B", Arrays.asList(new Book(1, "Java SE"), new Book(2, "C++"))),
				new Student(2, "a", Arrays.asList(new Book(3, "C"))), new Student(3, "C", Arrays.asList(new Book(4, "C#"), new Book(5, "Java EE"), new Book(6, "Android"))));
		students.stream().filter(s -> s.getBooks().stream().anyMatch(b -> b.getName().contains("Java"))).sorted(Comparator.comparing(Student::getName).reversed())
				.forEach(System.out::println);
	}

	@Test
	public void test20() {
		final Integer[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8 };
		final List<Integer> listOfIntegers = new ArrayList<>(Arrays.asList(intArray));

		System.out.println("listOfIntegers:");
		listOfIntegers.stream().forEach(e -> System.out.print(e + " "));
		System.out.println("");

		System.out.println("listOfIntegers sorted in reverse order:");
		final Comparator<Integer> normal = Integer::compare;
		final Comparator<Integer> reversed = normal.reversed();
		Collections.sort(listOfIntegers, reversed);
		listOfIntegers.stream().forEach(e -> System.out.print(e + " "));
		System.out.println("");

		System.out.println("Parallel stream");
		listOfIntegers.parallelStream().forEach(e -> System.out.print(e + " "));
		System.out.println("");

		System.out.println("Another parallel stream:");
		listOfIntegers.parallelStream().forEach(e -> System.out.print(e + " "));
		System.out.println("");

		System.out.println("With forEachOrdered:");
		listOfIntegers.parallelStream().forEachOrdered(e -> System.out.print(e + " "));
		System.out.println("");
	}

	class MySet extends TreeSet<Integer> {
		private static final long serialVersionUID = 2608547071947492231L;

		{
			System.out.println("here");
		}

		@Override
		public boolean add(Integer e) {
			System.out.println("add:" + e);
			return super.add(e);
		}

		@Override
		public boolean addAll(Collection<? extends Integer> c) {
			System.out.println("addAll:" + c);
			return super.addAll(c);
		}
	}

	@Test
	public void test19() {
		final Set<Integer> set = Arrays.asList(1, 3, 2, 5, 4, 8).stream().collect(MySet::new, MySet::add, MySet::addAll);
		System.out.println(set);
		final StringBuilder builder = Stream.of("a", "b", "c", "d", "e").collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
		System.out.println(builder);
		System.out.println(Stream.of("a", "b", "c", "d", "e", "f", "g").collect(Collectors.joining("#")));
	}

	@Test
	public void test18() {
		final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 5);
		System.out.println(list.stream().anyMatch(x -> x > 3));
		System.out.println(list.stream().mapToInt(x -> x).summaryStatistics());
		list.stream().distinct().forEach(System.out::print);
		System.out.println();
		list.stream().limit(3).forEach(System.out::print);
		System.out.println();
		list.stream().skip(3).forEach(System.out::print);
		System.out.println();
		Arrays.asList(2, 4, 3, 5, 7, 1, 6).stream().sorted().forEach(System.out::print);
	}

	@Test
	public void test17() {
		System.out.println(Arrays.asList("cC", "Dd", "eE", "Aa", "BB").stream().collect(Collectors.toSet()));// [cC, Dd, eE, Aa, BB]
		Arrays.asList(1, 2, 3, 4, 5).forEach(System.out::println);
		System.out.println("************");
		Arrays.stream(Arrays.asList(1, 2, 3, 4, 5).stream().toArray(Integer[]::new)).forEach(System.out::println);
	}

	@Test
	public void test16() throws IOException {
		Files.find(FileSystems.getDefault().getPath(System.getProperty("user.dir")), 10, (path, attribute) -> path.endsWith(Demo.class.getName().replace('.', '/') + ".java"))
				.forEach(path -> {
					try {
						Files.lines(path).forEach(System.out::println);
					} catch (final Exception e) {
						e.printStackTrace();
					}
				});
	}

	@Test
	public void test15() throws IOException {
		// Files.list(Paths.get("C:/Windows/System32")).forEach(System.out::println);
		Files.list(FileSystems.getDefault().getPath(System.getProperty("user.dir"))).forEach(System.out::println);
		System.out.println("******************************************************************");
		Files.walk(FileSystems.getDefault().getPath(System.getProperty("user.dir"))).forEach(System.out::println);
	}

	@Test
	public void test14() {
		System.out.println("ABCDE".length());
		System.out.println("ABCDE".chars().count());
		"ABCDE".chars().forEach(i -> System.out.println((char) i));
	}

	@Test
	public void test13() {
		final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		System.out.println(list.stream().reduce(0, (a, b) -> a + b));
		System.out.println(list.stream().reduce(0, Math::addExact));
		System.out.println(list.stream().reduce(0, Integer::sum));
		System.out.println(list.stream().filter(e -> e % 2 == 0).reduce(0, Math::addExact));
		System.out.println(list.stream().filter(e -> e % 2 != 0).reduce(0, Math::addExact));
		System.out.println(list.stream().mapToInt(e -> e * 2).sum());
		System.out.println(list.stream().mapToInt(e -> e).sum());
		System.out.println(IntStream.range(1, 11).sum());
	}

	@Test
	public void test12() {
		System.out.println(Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10).stream().filter(e -> e > 3 && e % 2 == 0).map(e -> e * 2).findFirst().get());
	}

	@Test
	public void test11() {
		Arrays.asList(1, 2, 3, 4, 5).stream().map(e -> isPrime(e)).forEach(System.out::println);
	}

	private boolean isPrime(int n) {
		return n > 1 && IntStream.range(2, n).noneMatch(i -> n % i == 0);
	}

	@Test
	public void test10() {
		final List<String> list = Arrays.asList(" a ", "    b", "c    ");
		list.stream().map(String::trim).collect(Collectors.toList()).forEach(System.out::print);
		System.out.printf("%n%s%n", list);
		for (final ListIterator<String> iterator = list.listIterator(); iterator.hasNext();) {
			iterator.set(iterator.next().trim());
		}
		list.forEach(System.out::print);
	}

	@Test
	public void test9() {
		final TreeSet<Object> treeSet = new TreeSet<>();
		new TreeSet<>(treeSet);
		final Map<String, String> map = new TreeMap<>();
		map.put("1", "c");
		map.put("2", "b");
		map.put("3", "a");
		System.out.println(map.values());
		System.out.println(Arrays.toString(map.values().toArray()));
	}

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
