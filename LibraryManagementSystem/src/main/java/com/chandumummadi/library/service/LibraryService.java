package com.chandumummadi.library.service;

import com.chandumummadi.library.model.Book;
import com.chandumummadi.library.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.chandumummadi.library.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books;
    private List<User> users;

    public LibraryService() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    // Add a book to the library
    public void addBook(Book book) {
        String SQL = "INSERT INTO books(title, author, isbn, genre, total_copies, borrowed_copies) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getGenre());
            pstmt.setInt(5, book.getTotalCopies());
            pstmt.setInt(6, book.getBorrowedCopies());

            pstmt.executeUpdate();
            System.out.println("Book added to the database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Remove a book from the library
    public void removeBook(String isbn) {
        String SQL = "DELETE FROM books WHERE isbn = ?";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, isbn);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("Book with ISBN " + isbn + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Register a new user
    public void registerUser(User user) {
        String SQL = "INSERT INTO users(user_id, name, contact_info) VALUES(?, ?, ?)";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getContactInfo());

            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Issue a book to a user
    public void issueBook(String isbn, String userId) {
        String checkBookSQL = "SELECT total_copies, borrowed_copies FROM books WHERE isbn = ?";
        String checkUserSQL = "SELECT id FROM users WHERE user_id = ?";
        String updateBookSQL = "UPDATE books SET borrowed_copies = borrowed_copies + 1 WHERE isbn = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement checkBookStmt = conn.prepareStatement(checkBookSQL);
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSQL);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSQL)) {

            // Check if the book is available
            checkBookStmt.setString(1, isbn);
            ResultSet bookResult = checkBookStmt.executeQuery();
            if (bookResult.next()) {
                int totalCopies = bookResult.getInt("total_copies");
                int borrowedCopies = bookResult.getInt("borrowed_copies");

                if (borrowedCopies < totalCopies) {
                    // Check if the user exists
                    checkUserStmt.setString(1, userId);
                    ResultSet userResult = checkUserStmt.executeQuery();
                    if (userResult.next()) {
                        // Issue the book (update borrowed_copies)
                        updateBookStmt.setString(1, isbn);
                        updateBookStmt.executeUpdate();
                        System.out.println("Book issued successfully to user: " + userId);
                    } else {
                        System.out.println("User not found.");
                    }
                } else {
                    System.out.println("No copies available for this book.");
                }
            } else {
                System.out.println("Book not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Return a book from a user
    public void returnBook(String isbn, String userId) {
        String checkBookSQL = "SELECT borrowed_copies FROM books WHERE isbn = ?";
        String checkUserSQL = "SELECT id FROM users WHERE user_id = ?";
        String updateBookSQL = "UPDATE books SET borrowed_copies = borrowed_copies - 1 WHERE isbn = ?";

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement checkBookStmt = conn.prepareStatement(checkBookSQL);
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSQL);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSQL)) {

            // Check if the book is currently issued
            checkBookStmt.setString(1, isbn);
            ResultSet bookResult = checkBookStmt.executeQuery();
            if (bookResult.next()) {
                int borrowedCopies = bookResult.getInt("borrowed_copies");

                if (borrowedCopies > 0) {
                    // Check if the user exists
                    checkUserStmt.setString(1, userId);
                    ResultSet userResult = checkUserStmt.executeQuery();
                    if (userResult.next()) {
                        // Return the book (update borrowed_copies)
                        updateBookStmt.setString(1, isbn);
                        updateBookStmt.executeUpdate();
                        System.out.println("Book returned successfully by user: " + userId);
                    } else {
                        System.out.println("User not found.");
                    }
                } else {
                    System.out.println("No borrowed copies to return for this book.");
                }
            } else {
                System.out.println("Book not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Helper method to find a book by ISBN
    public Book findBookByIsbn(String isbn) {
        String SQL = "SELECT * FROM books WHERE isbn = ?";

        // Try-with-resources to auto-close the ResultSet and PreparedStatement
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            // Set the ISBN parameter before executing the query
            pstmt.setString(1, isbn);

            try (ResultSet rs = pstmt.executeQuery()) {  // Ensure ResultSet is also handled with try-with-resources

                if (rs.next()) {
                    // Extract book data from the ResultSet and create a Book object
                    return new Book(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getString("genre"),
                            rs.getInt("total_copies"),
                            rs.getInt("borrowed_copies")
                    );

                } else {
                    System.out.println("Book not found with ISBN: " + isbn);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // If no book was found, return null
        return null;
    }




    // Helper method to find a user by ID
    public User findUserById(String userId) {
        String SQL = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("contact_info")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // Get all available books
    public List<Book> getAvailableBooks() {
        String SQL = "SELECT * FROM books WHERE borrowed_copies < total_copies";
        List<Book> availableBooks = new ArrayList<>();

        try (Connection conn = new DatabaseConnection().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Create a new Book object and add it to the list
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getString("genre"),
                        rs.getInt("total_copies"),
                        rs.getInt("borrowed_copies")
                );
                availableBooks.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return availableBooks;
    }

}
