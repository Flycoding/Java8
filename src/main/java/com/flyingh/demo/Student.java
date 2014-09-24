package com.flyingh.demo;

import java.util.List;

public class Student {
	private int id;
	private String name;
	private List<Book> books;

	public Student() {
		super();
	}

	public Student(int id, String name, List<Book> books) {
		this.id = id;
		this.name = name;
		this.books = books;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", books=" + books + "]";
	}
}
