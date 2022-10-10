/**
 * 
 * @author Robert Miller
 * 09/25/2022
 * CMSC 495/6381
 * Library class. Currently holds all books in an arraylist, and has a public method to search this list. 
 *
 * 10/09/22 Updates by Robert Miller
 * 	Added methods to import and update xml file (library.xml)
 * 	Check if library.xml exists, if not setup a basic library with 5 books
 * 	Admin can add new books to the xml from the LibraryGui class
 *  Checkout status as well as the user who checked out is saved to xml file
 *  
 */

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Library {

	ArrayList<Book> bookObjectsList = new ArrayList<Book>();	
	static String libraryXMLPath = System.getProperty("user.dir") + "/library.xml";
	
	InputStream inputStream;
	
	// load book database
	public Library() {
		// temporarily add a few dummy books for testing functionality

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(libraryXMLPath)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            NodeList listOfBooks = doc.getElementsByTagName("book");
            //System.out.println(listOfStaff.getLength()); // 2

            for (int i = 0; i < listOfBooks.getLength(); i++) {
                // get first staff
                Node bookItem = listOfBooks.item(i);
                if (bookItem.getNodeType() == Node.ELEMENT_NODE) {
                    String isbn = bookItem.getAttributes().getNamedItem("isbn").getTextContent();
                    String title = "";
                    String author = "";
                    String checkout = "";
                    
                    NodeList childNodes = bookItem.getChildNodes();
                    
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {

                            if ("title".equalsIgnoreCase(item.getNodeName())) {
                                title = item.getTextContent();
                            }
                            if ("author".equalsIgnoreCase(item.getNodeName())) {
                                // remove xml element `name`
                                author = item.getTextContent();
                            }
                            if ("checkout".equalsIgnoreCase(item.getNodeName())) {
                                // remove xml element `name`
                                checkout = item.getTextContent();
                            }
                        }
                        
                    }
                    
                    Book tmpBook = new Book(author, title, isbn, checkout);
                    bookObjectsList.add(tmpBook);
                                        
                    System.out.println("ISBN: " + isbn);
                    System.out.println("Author: " + author);
                    System.out.println("Title: " + title);
                           
                }

            }

            is.close();

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException f) {
        	addFillerBooks();
        }
	}
	
	// if there is no library found, create one
	private void addFillerBooks() {
		try {
			System.out.println("Adding Filler Books");
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	        // root elements
	        Document docXML = docBuilder.newDocument();
	        Element rootElement = docXML.createElement("library");
	        docXML.appendChild(rootElement);
            
            //write the content into xml file
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(docXML);

            StreamResult result =  new StreamResult(new File(libraryXMLPath));
            transformer.transform(source, result);
           
	    	addNewBook("Anna Karenina", "Leo Tolstoy", "9780075536321");
	    	addNewBook("Madame Bovary", "Gustav Flaubert", "9780393096088");
	    	addNewBook("War and Peace", "Leo Tolstoy", "1400079985");
	    	addNewBook("Lolita", "Vladimir Nabokov", "0141182539");
	    	addNewBook("The Adventures of Huckleberry Finn", "Mark Twain", "9780553210798");	
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Takes an title as argument and returns any matches in an arraylist
	public ArrayList<Book> findBooks(String title) {
		
		ArrayList<Book> bookObjectsFoundList = new ArrayList<Book>();
		
		System.out.println("Book objects in list: " + bookObjectsList.size());
	
		for (int i = 0; i < bookObjectsList.size(); i++) {
			Book bookToIterate = (Book) bookObjectsList.get(i);
			
			if (bookToIterate.getTitle().contains(title))
				bookObjectsFoundList.add(bookToIterate);
		}
		
		return bookObjectsFoundList;
	
	}
	
	public ArrayList<Book> findCheckedOutBooks(String userName) {
		
		ArrayList<Book> bookObjectsFoundList = new ArrayList<Book>();
				
		for (int i = 0; i < bookObjectsList.size(); i++) {
			Book bookToIterate = (Book) bookObjectsList.get(i);
			
			if (bookToIterate.getCheckedOut().contains(userName))
				bookObjectsFoundList.add(bookToIterate);
		}
		
		return bookObjectsFoundList;
	
	}
	
	// pass string of username that checked out book, or blank string if it is returned
	public void updateCheckoutStatus(Book checkoutBook, String username) {
		
		// if book is being checked out
		if (username.length() > 0) {
			checkoutBook.setCheckedOutStatus(true);
			checkoutBook.checkout(username);
		}
		// else it is being returned
		else {
			checkoutBook.setCheckedOutStatus(false);
			checkoutBook.checkout("");
		}
		
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(libraryXMLPath)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            NodeList listOfStaff = doc.getElementsByTagName("book");
            //System.out.println(listOfStaff.getLength()); // 2

            for (int i = 0; i < listOfStaff.getLength(); i++) {
                // get first staff
                Node book = listOfStaff.item(i);
                if (book.getNodeType() == Node.ELEMENT_NODE) {
                    String isbn = book.getAttributes().getNamedItem("isbn").getTextContent();
                    if (checkoutBook.getISBN().equals(isbn.trim())) {

                        NodeList childNodes = book.getChildNodes();

                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node item = childNodes.item(j);
                            if (item.getNodeType() == Node.ELEMENT_NODE) {

                                if ("checkout".equalsIgnoreCase(item.getNodeName())) {
                                	item.setTextContent(username);
                                }
        	
                            }
                        }
                        
                    }
                }
                    
            }
            
            //write the content into xml file
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(libraryXMLPath));
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | SAXException
                | IOException | TransformerException e) {
            e.printStackTrace();
        }
	}
	
	public void addNewBook(String title, String author, String ISBN) throws ParserConfigurationException, TransformerException { 
		
		Book newBook = new Book(author, title, ISBN, "");
		bookObjectsList.add(newBook);
		
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(libraryXMLPath)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);
            
            Node library = doc.getDocumentElement();
            
            System.out.println("lib: " + library.getNodeName());
                        
            // add a new xml element, book
            Element newBookXML = doc.createElement("book");
            newBookXML.setAttribute("isbn", ISBN);
            
            // add a new xml element, title
            Element newTitleXML = doc.createElement("title");
            newTitleXML.appendChild(doc.createTextNode(title));
            
            // add a new xml element, title
            Element newAuthorXML = doc.createElement("author");
            newAuthorXML.appendChild(doc.createTextNode(author));
            
            // add a new xml element, title
            Element newCheckoutXML = doc.createElement("checkout");
            newCheckoutXML.appendChild(doc.createTextNode(""));
            
            newBookXML.appendChild(newTitleXML);
            newBookXML.appendChild(newAuthorXML);
            newBookXML.appendChild(newCheckoutXML);
            
            library.appendChild(newBookXML);
            
            //write the content into xml file
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(libraryXMLPath));
            transformer.transform(source, result);
            
            /*
	        // write dom document to a file
	        try (FileOutputStream output =
	                     new FileOutputStream(libraryXMLPath)) {
	            writeXml(doc, output);
	        } 
	        */
            is.close();
        
        } catch (ParserConfigurationException | SAXException
                | IOException | TransformerException e) {
            e.printStackTrace();
        }
	}
	
	private void writeLibraryXML() {
		//Create the XML

	}
	
    // write doc to output stream
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer(
                new StreamSource(new File(libraryXMLPath)));

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
