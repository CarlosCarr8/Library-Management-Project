package com.library.app;

import com.library.manager.DatabaseManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.library.entity.Book;

public class Main {

	Public static void main(String[] args) {
		DatabaseManager.setup();
		
		
		Session session = DatabaseManager.getSessionFactory().openSession();
		
		Transaction tx = session.beginTransaction();
		
		Book book = new Book("The little prince", "9781529047967", true);
		session.persist(book);
		
		
		
		
		tx.commit();
		
		
		session.close();
		DatabaseManager.exit();
		
		System.out.println("Book created successfully!");
	}
}
