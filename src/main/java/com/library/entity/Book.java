package com.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.library.entity.Genre;


@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String isbn;
	private String author;
	private LocalDate publicationDate;
	private boolean available;
	
	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
	public Book() {
		
	}
	
	public Book(String title, String isbn, String author, LocalDate publicationDate,  boolean available, Genre genre) {
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.publicationDate = publicationDate;
		this.available = available;
		this.genre = genre;
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
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public LocalDate getPublicationDate() {
		return publicationDate;
	}
	
	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public boolean getAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	
	@Override
	public String toString() {
		return "Book [id=" + id + 
				", title= " + title + 
				", isbn= " + isbn + 
				", author= " + author + 
				", publicationDate= " + publicationDate + 
				", available= " + available + "]";
			
	}
	
	
}
	
	
	
	
	
	
	
	
