package com.library.app;
import com.library.manager.DatabaseManager;
import com.library.manager.LibraryManager;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
    	
    			// StartHibernate
    	DatabaseManager.setup();
    			
    			// Create LibraryManager object
    	LibraryManager manager = new LibraryManager();
    	

        		// Add some genres
        manager.addGenre("Fantasy");
        manager.addGenre("Science Fiction");
        manager.addGenre("Drama");
        manager.viewGenres();

        		// Add books and link them with genre IDs
        		// (ID 1 = Fantasy, ID 2 = Science Fiction)
        manager.addBook("Harry Potter", "9780747532743", "J.K. Rowling", LocalDate.of(1997, 6, 26), true, 1);
        manager.addBook("Dune", "9780441172719", "Frank Herbert", LocalDate.of(1965, 8, 1), true, 2);
        manager.addBook("The Little Prince", "9781529047967", "Antoine de Saint-Exupéry", LocalDate.of(1943, 4, 6), true, 3);

        		// View all books
        manager.viewBooks();

        		// Search examples
        manager.searchGenre("Fantasy");
        manager.searchByAuthor("Frank Herbert");
        manager.searchYear(1997);

        		// Mark one book as unavailable
        manager.markBookUnavailable(1);

        		// View updated book list
        manager.viewBooks();

        		// EndHibernate session 
        DatabaseManager.exit();

        System.out.println("First sucessful test");
        
        
        
//    	manager.clearDatabase();
    }
}

