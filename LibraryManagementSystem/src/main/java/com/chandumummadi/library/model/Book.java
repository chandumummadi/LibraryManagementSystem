package com.chandumummadi.library.model;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int totalCopies;
    private int borrowedCopies;

    public Book(String title, String author, String isbn, String genre, int totalCopies, int borrowedCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.borrowedCopies = borrowedCopies;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getBorrowedCopies() {
        return borrowedCopies;
    }

    public void borrowBook() {
        if (borrowedCopies < totalCopies) {
            this.borrowedCopies++;
        }
    }

    public void returnBook() {
        if (borrowedCopies > 0) {
            this.borrowedCopies--;
        }
    }
}
