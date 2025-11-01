package com.library.manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.library.entity.Book;

public class LibraryManager {
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	
}
