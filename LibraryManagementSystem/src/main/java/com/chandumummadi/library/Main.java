package com.chandumummadi.library;

import com.chandumummadi.library.model.Book;
import com.chandumummadi.library.model.User;
import com.chandumummadi.library.service.LibraryService;
import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Sample books and users to test
        Book book1 = new Book("Effective Java", "Joshua Bloch", "9780134685991", "Programming", 5 , 0);
        Book book2 = new Book("Clean Code", "Robert C. Martin", "9780132350884", "Programming", 3 , 0);
        libraryService.addBook(book1);
        libraryService.addBook(book2);

        User user1 = new User("u001", "Alice", "alice@example.com");
        libraryService.registerUser(user1);

        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Register User");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Show Available Books");  // New option
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter total copies: ");
                    int totalCopies = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Book newBook = new Book(title, author, isbn, genre, totalCopies , 0);
                    libraryService.addBook(newBook);
                    System.out.println("Book added successfully.");
                    break;

                case 2:
                    System.out.print("Enter ISBN of the book to remove: ");
                    String removeIsbn = scanner.nextLine();
                    libraryService.removeBook(removeIsbn);
                    System.out.println("Book removed successfully.");
                    break;

                case 3:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter contact info: ");
                    String contactInfo = scanner.nextLine();

                    User newUser = new User(userId, userName, contactInfo);
                    libraryService.registerUser(newUser);
                    System.out.println("User registered successfully.");
                    break;

                case 4:
                    System.out.print("Enter ISBN of the book to issue: ");
                    String issueIsbn = scanner.nextLine();
                    System.out.print("Enter user ID to issue the book to: ");
                    String issueUserId = scanner.nextLine();

                    libraryService.issueBook(issueIsbn, issueUserId);
                    break;

                case 5:
                    System.out.print("Enter ISBN of the book to return: ");
                    String returnIsbn = scanner.nextLine();
                    System.out.print("Enter user ID to return the book: ");
                    String returnUserId = scanner.nextLine();

                    libraryService.returnBook(returnIsbn, returnUserId);
                    break;

                case 6:  // Show all available books
                    List<Book> availableBooks = libraryService.getAvailableBooks();
                    if (availableBooks.isEmpty()) {
                        System.out.println("No books are available at the moment.");
                    } else {
                        System.out.println("\nAvailable Books:");
                        for (Book book : availableBooks) {
                            System.out.println(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getIsbn() + ")");
                        }
                    }
                    break;

                case 7:
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
