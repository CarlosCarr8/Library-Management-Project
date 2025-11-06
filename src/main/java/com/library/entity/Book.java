package com.library.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String isbn;
	private boolean available;
	
	
	public Book() {
		
	}
	
	public Book(String title, String isbn, boolean available) {
		this.title = title;
		this.isbn = isbn;
		this.available = available;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public boolean getAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title= " + title + ", isbn= " + isbn + "available= " + available + "]";
	}
	
	
}
	
	
	
	
	
	
	
	
