/*
 * Book.java
 * 9/26/2022
 * Kyle Pope
 * Subclass to create and manage a Book object.
 */

public class Book {

    private String author, title, isbn, checkout;
    private boolean isCheckedOut;

    // Constructor
    public Book(String author, String title, String isbn, String checkout) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.checkout = checkout;
        
        // if there is a username in the checkout field
        if (checkout.length() > 0)
        	this.isCheckedOut = true;
        else
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

    public String getCheckedOut() {
        return checkout;
    }
    
    // checkout field is set to user's name if it is checked out
    public void checkout(String username) {
    	checkout = username;
    }
    
    public boolean getCheckedOutStatus() {
        return isCheckedOut;
    }

    // Method to set checked out status
    public void setCheckedOutStatus(boolean newStatus) {
        this.isCheckedOut = newStatus;
    }
}
