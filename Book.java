/*
 * Book.java
 * 9/26/2022
 * Kyle Pope
 * Subclass to create and manage a Book object.
 */

public class Book {

    private String author, title, isbn;
    private boolean isCheckedOut;

    // Constructor
    public Book(String author, String title, String isbn) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.isCheckedOut = false;
    }

    // Getters
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return isbn;
    }

    public boolean getCheckedOutStatus() {
        return isCheckedOut;
    }

    // Method to set checked out status
    public void setCheckedOutStatus(boolean newStatus) {
        this.isCheckedOut = newStatus;
    }
}
