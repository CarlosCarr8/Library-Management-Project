package com.library.manager;
import com.library.entity.Genre;
import com.library.entity.User;
import com.library.entity.Member;
import com.library.entity.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.library.entity.Book;
import java.time.LocalDate;
import java.util.List;


public class LibraryManager {
	
	// To add a book
	
	public void addBook(String title, String isbn, String author, LocalDate publicationDate, boolean available, int genreID) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Genre genre = session.get(Genre.class, genreID);
		
		Book book = new	Book(title, isbn, author, publicationDate, available, genre);
		session.persist(book);
		
		tx.commit();
		session.close();
		
		System.out.println("Book added: " + title);
	}
	
	// To view all the books (read from the database)
	
	public void viewBooks() {
		Session session =  DatabaseManager.getSessionFactory().openSession();
		
		var books = session.createQuery("from Book", Book.class).list();
		
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
		
		var results = session.createQuery("from Book where lower(author) like :author and available = true", Book.class)
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
		
		var results  = session.createQuery("from Book where year(publicationDate) = :year and available = true", Book.class)
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
	
	
	// to add a new Genre
	
	public void addGenre(String type) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
				
		Genre genre = new Genre(type);
		session.persist(genre);
		
		tx.commit();
		session.close();
	}
	
	// to view all genres
	
	public void viewGenres() {
		Session session = DatabaseManager.getSessionFactory().openSession();
		var genres = session.createQuery("from Genre", Genre.class).list();
		
		for (Genre g : genres) {
			System.out.println(g);
		}
		session.close();
	}
	
	// to search books by genre
	
	public void searchGenre(String genreName) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		var results = session.createQuery("from Book where lower(genre.type) like :gname and available = true", Book.class)
				.setParameter("gname", "%" + genreName.toLowerCase() + "%").list();
		
		if (results.isEmpty()) {
			System.out.println("No books found in genre '" + genreName + "'");
		} else {
			System.out.println("Books by genre '" + genreName + "':");
			
			for (Book b : results) {
				System.out.println(b);
			}
		}
		session.close();
	}
	
	
	// method to borrow books
	
	public void borrowBook(int memberId, int bookId) {
		Session session = DatabaseManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			
			// first find the member by member id (what would be student number instead of DB id)
			Member member = session.createQuery("from Member m where m.memberId = mid", Member.class)
					.setParameter("mid", memberId)
					.uniqueResult();
			
			if(member==null) {
				System.out.println("Member ID: " + memberId + "not found.");
				tx.rollback();
				return;
			}
			
			//Find the book by its DB id
			
			Book book = session.get(Book.class, bookId);
			
			if(book==null) {
				System.out.println("Book ID: " + bookId + "not found.");
				tx.rollback();
				return;
			}
			
			//check if the book is available
			
			if(!book.getAvailable()) {
				System.out.println("The book: " + book.getTitle() + " is not available.");
				tx.rollback();
				return;
				
			}
			
			LocalDate today = LocalDate.now();
			
			//check if the member has overdue loans
			Long overdueCount = session.createQuery("select count(1) from Loan 1" + 
					"where 1.member = m: and 1.returned = false and 1.dueDate <:today",
					Long.class)
			.setParameter("m", member)
			.setParameter("today", today)
			.uniqueResult();
			
			if (overdueCount != null && overdueCount>0) {
				System.out.println("Member: '" + member.getName() + "' has overdue loans and cannot borrow a new book.");
				tx.rollback();
				return;
			}
			
			// check how many current loans the member has (max 5)
			
			Long activeLoanCount = session.createQuery("select count(1) from loan 1 " +
			"where 1.member = :m and 1.returned = false",
			Long.class)
					.setParameter("m", member)
					.uniqueResult();
			
			if (activeLoanCount != null && activeLoanCount >= 5) {
				System.out.println("Member: '" + member.getName() + "' already has the maximum amount of loans (5).");
			}
			
			// check if the member already has an active loan for this book
			Long sameBookActive  = session.createQuery("select count(1) from Loan 1" +
					"where 1.member = :m and 1.book = :b and 1.returned = false",
					Long.class)
					.setParameter("m", member)
					.setParameter("b", book)
					.uniqueResult();
			
			if(sameBookActive != null && sameBookActive > 0) {
				System.out.println("Member: '" + member.getName() + "' already has an active loan for this book.");
				tx.rollback();
				return;
			}
			
			
			
			// now if the member is applicable, create a new loan
			
			LocalDate dueDate = today.plusDays(14);
			Loan loan = new Loan(member, book, today, dueDate);
			session.persist(loan);
			
			book.setAvailable(false);
			session.merge(book);
			
			tx.commit();
			System.out.println("Book: '" + book.getTitle() +
								"' loaned to " + member.getName() +
								"until " + dueDate + ".");
		} catch (Exception e){
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
	
	
	
	
	
	
	public boolean login(String username, String password) {
	    Session session = DatabaseManager.getSessionFactory().openSession();

	    try {
	        User user = session.createQuery("from User where username = :uname", User.class)
	                .setParameter("uname", username)
	                .uniqueResult();

	        if (user == null) {
	            System.out.println("Username not found.");
	            return false;
	        }

	        if (!user.login(password)) { 
	            System.out.println("Incorrect password.");
	            return false;
	        }

	        System.out.println("Login successful! Welcome: " + username);
	        return true;

	    } finally {
	        session.close();
	    }
	}
	
	
	
	public boolean resetPassword(String username, String newPassword) {
	    Session session = DatabaseManager.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {
	        User user = session.createQuery("from User where username = :uname", User.class)
	                .setParameter("uname", username)
	                .uniqueResult();

	        if (user == null) {
	            System.out.println("User not found.");
	            return false;
	        }

	        user.setPassword(newPassword);  
	        session.merge(user);
	        tx.commit();

	        System.out.println("Password updated for user: " + username);
	        return true;

	    } catch (Exception e) {
	        tx.rollback();
	        System.out.println("Error resetting password: " + e.getMessage());
	        return false;

	    } finally {
	        session.close();
	    }
	}
	
}
