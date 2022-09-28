/**
 * 
 * @author Robert Miller
 * 09/25/2022
 * CMSC 495/6381
 * Library class. Currently holds all books in an arraylist, and has a public method to search this list. 
 *
 */

import java.util.ArrayList;

public class Library {

	ArrayList<Book> bookObjectsList = new ArrayList<Book>();	
	
	// load book database
	public Library() {
		// temporarily add a few dummy books for testing functionality
		Book book = new Book("Leo Tolstoy", "Anna Karenina", "V1182000292");
		Book book2 = new Book("Gustav Flaubert", "Madame Bovary", "V1182000292");
		Book book3 = new Book("Leo Tolstoy", "War and Peace", "V11820002292");
		Book book4 = new Book("Vladimir Nabokov", "Lolita", "V1182000292");
		Book book5 = new Book("Mark Twain", "The Adventures of Huckleberry Finn", "V1182000292");
		
		bookObjectsList.add(book);
		bookObjectsList.add(book2);
		bookObjectsList.add(book3);
		bookObjectsList.add(book4);
		bookObjectsList.add(book5);
	}
	
	// Takes an title as argument and returns any matches in an arraylist
	public ArrayList<Book> findBooks(String title) {
		
		ArrayList<Book> bookObjectsFoundList = new ArrayList<Book>();
				
		for (int i = 0; i < bookObjectsList.size(); i++) {
			Book bookToIterate = (Book) bookObjectsList.get(i);
			
			if (bookToIterate.getTitle().contains(title))
				bookObjectsFoundList.add(bookToIterate);
		}
		
		return bookObjectsFoundList;
	
	}
}
