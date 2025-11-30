package com.library.app;

import java.time.LocalDate;
import java.util.Scanner;

import com.library.manager.DatabaseManager;
import com.library.manager.LibraryManager;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static LibraryManager manager = new LibraryManager();

    public static void main(String[] args) {

        DatabaseManager.setup();  
        runMenu();
        DatabaseManager.exit();
    }

    public static void runMenu() {

        while (true) {

            System.out.println("\n====== LIBRARY MANAGEMENT SYSTEM ======");
            System.out.println("1. Initialize sample data (genres, books, members)");
            System.out.println("2. Add Genre");
            System.out.println("3. Add Book");
            System.out.println("4. Add Member");
            System.out.println("5. View All Books");

            System.out.println("6. Search Books by Title");
            System.out.println("7. Search Books by Author");
            System.out.println("8. Search Books by Year");
            System.out.println("9. Search Books by Genre ID");
            System.out.println("10. Search Books by Genre Name");

            System.out.println("11. Borrow Book");
            System.out.println("12. Return Book");
            System.out.println("13. View Active Loans");
            System.out.println("14. View Overdue Loans");
            System.out.println("15. View Loans by Member");
            System.out.println("16. Mark book as 'Unavailable'");

            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {

                case 1 -> initializeSampleData();

                case 2 -> {
                    System.out.print("Enter genre name: ");
                    String g = sc.nextLine();
                    manager.addGenre(g);
                }

                case 3 -> addBookMenu();

                case 4 -> addMemberMenu();

                case 5 -> manager.viewBooks();

                case 6 -> {
                    System.out.print("Enter title keyword: ");
                    String t = sc.nextLine();
                    manager.searchByTitle(t);
                }

                case 7 -> {
                    System.out.print("Enter author name: ");
                    String a = sc.nextLine();
                    manager.searchByAuthor(a);
                }

                case 8 -> {
                    System.out.print("Enter publication year: ");
                    int y = sc.nextInt();
                    manager.searchYear(y);
                }

                case 9 -> {
                    System.out.print("Enter Genre ID: ");
                    int gid = sc.nextInt();
                    manager.searchByGenre(gid);
                }

                case 10 -> {
                    System.out.print("Enter Genre name: ");
                    String gname = sc.nextLine();
                    manager.searchGenre(gname);
                }

                case 11 -> {
                    System.out.print("Enter Member ID: ");
                    int mid = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();
                    manager.borrowBook(mid, bid);
                }

                case 12 -> {
                    System.out.print("Enter Loan ID: ");
                    int lid = sc.nextInt();
                    manager.returnBook(lid);
                }

                case 13 -> manager.viewBorrowedBooks();

                case 14 -> manager.viewOverdueBooks();

                case 15 -> {
                    System.out.print("Enter Member ID: ");
                    int mid2 = sc.nextInt();
                    manager.viewLoansByMember(mid2);
                }
                
                case 16 -> {
                	System.out.println("Enter BookID to mark unavailable: ");
                	int id = sc.nextInt();
                	manager.markBookUnavailable(id);
                }

                case 0 -> {
                    System.out.println("Goodbye.");
                    return;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }

    // MENU HELPERS

    private static void addBookMenu() {
        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        System.out.print("Author: ");
        String author = sc.nextLine();

        System.out.print("Publication year: ");
        int y = sc.nextInt();
        System.out.print("Publication month: ");
        int m = sc.nextInt();
        System.out.print("Publication day: ");
        int d = sc.nextInt();
        sc.nextLine();

        LocalDate pubDate = LocalDate.of(y, m, d);

        System.out.print("Genre ID: ");
        int gid = sc.nextInt();
        sc.nextLine();

        manager.addBook(title, isbn, author, pubDate, gid);
    }

    private static void addMemberMenu() {

        System.out.print("Member ID (student number): ");
        int mid = sc.nextInt();
        sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        System.out.print("Full name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        manager.addMember(mid, username, pass, name, email);
    }

    // SAMPLE DATA
    private static void initializeSampleData() {

        // GENRES
        manager.addGenre("Fantasy");
        manager.addGenre("Drama");
        manager.addGenre("Science Fiction");

        // BOOKS
        manager.addBook("Harry Potter", "9780747532743", "J.K. Rowling",
                LocalDate.of(1997, 6, 26), 1);

        manager.addBook("Dune", "9780441172719", "Frank Herbert",
                LocalDate.of(1965, 8, 1), 3);

        manager.addBook("The Little Prince", "9781529047967", "Antoine de Saint-Exupéry",
                LocalDate.of(1943, 4, 6), 2);

        // MEMBERS
        manager.addMember(52842, "Carlos", "pass123", "Carlos Carrillo", "52842@alunos.upt.pt");
        manager.addMember(53139, "Gulfarida", "password123", "Gulfarida Zhanabergen", "53139@alunos.upt.pt");

        System.out.println("Sample data inserted successfully!");
    }
}

