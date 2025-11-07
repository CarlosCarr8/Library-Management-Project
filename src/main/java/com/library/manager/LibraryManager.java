package com.library.manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.library.entity.Book;

public class LibraryManager {
	
	// To add a book
	
	public void addBook(String title, String isbn, boolean available) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Book book = new	Book(title, isbn, available);
		session.persist(book);
		
		tx.commit();
		session.close();
		
		System.out.println("Book added: " + title);
	}
	
	// To view all the books (read from the database)
	
	public void viewBooks() {
		Session session =  DatabaseManager.getSessionFactory().openSession();
		
		var books = session.createQuery("from book", Book.class).list();
		
		System.out.println("\n Book list: ");
		for (Book b : books) {
			System.out.println(b);
		}
		
		session.close();
	}
		
	// Delete a book without literally erasing it (mark it as unavailable)
		
	public void markBookUnavailable(int id) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Book book = session.get(Book.class, id);
		if (book != null) {
			if (book.getAvailable()) {
				book.setAvailable(false);
				session.merge(book);
				System.out.println("Book marked as unavailable: " + book.getTitle());
			} else {
				System.out.println("Book is already unavailable.");
			}
		} else {
			System.out.println("Book not found!");
		}
		tx.commit();
		session.close();
		
	}
	
	// To search a book by title
	
	public void searchByTitle(String keyword) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		
		var results = session.createQuery("from Book where lower(title) like :keyword and available = true", Book.class)
				.setParameter("keyword", "%" + keyword.toLowerCase() + "%").list();
		
		if (results.isEmpty()) {
			System.out.println("No available books found for: " + keyword);
		} else {
			System.out.println("Search results by title:");
			for (Book b: results) {
				System.out.println(b);
			}
		}
		
		session.close();
	}
	
	// To search by author
	
	public void searchByAuthor(String authorName) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		
		var results = session.createQuery("from Book where lower(author) like :keyword and available = true", Book.class)
				.setParameter("author", "%" + authorName.toLowerCase() + "%").list();
		
		if (results.isEmpty()) {
			System.out.println("No available books found for: " + authorName);
		} else {
			System.out.println("Search results by author:");
			for (Book b: results) {
				System.out.println(b);
			}
		}
		
		session.close();
	}
	
	// To search by publication year
	
	public void searchYear(int year) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		
		var results  = session.createQuery("from Book where year(publicationDate) = :year and available == true", Book.class)
				.setParameter("year", year).list();
		
		if (results.isEmpty()) {
			System.out.println("No available books found for: " + year);
		} else {
			System.out.println("Search results by author:");
			for (Book b: results) {
				System.out.println(b);
			}
		}
		
		session.close();
	}
	
	
	
	
	
	
	
	
	
}
