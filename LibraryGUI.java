/**
 * 
 * @author Robert Miller
 * 09/27/2022
 * CMSC 495/6381
 * LibraryGUI class. Currently displays search panel and outputs search results. Allows for basic checkout. 
 *
 * 10/03/22 Updates by Robert Miller:
 * 	Added User registration menu item
 *  Added createRegistationGui method
 *  Added JPanel createRegistration JPanel
 *  Modified class to integrate new registration panel and login
 *  
 * 10/09/22 Updates by Robert Miller
 * Added add / remove books menu item and gui panel for admin users and check for user sign in	
 * Added MyAccount menu item and gui panel to display a list of checked out users
 * 	Users can return books for this panel
 * Updated search results to update checkout status of books as they are checked out
 * 
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class LibraryGUI implements ActionListener, Runnable {

    private JFrame  frame;
    private JPanel  panel;
    Container pane;
    
	private JLabel searchLabel;
	private JTextField searchField;
    private JButton searchButton;
    
    private boolean userSignedIn = false;
    private String currentUser = null;
    
	JButton loginButton;
	
	// login components
	JLabel usernameLabel;
	JTextField usernameField;
	JLabel passwordLabel;
	JPasswordField passwordField;
	JButton submitLoginButton;
    
	// registration components
	JLabel firstNameLabel;
	JTextField firstNameField;
	JLabel lastNameLabel;
	JTextField lastNameField;
	JLabel streetAddressLabel;
	JTextField streetAddressField;
	JLabel cityLabel;
	JTextField cityField;
	JLabel stateLabel;
	JTextField stateField;
	JLabel zipCodeLabel;
	JTextField zipCodeField;
	JLabel emailLabel;
	JTextField emailField;
	JLabel registerUsernameLabel;
	JTextField registerUsernameField;
	JLabel registerPasswordLabel;
	JPasswordField registerPasswordField;
	JButton submitUserRegistertationButton;
	
	JLabel newBookTitleLabel;
	JTextField newBookTitleField;
	JLabel newBookAuthorLabel;
	JTextField newBookAuthorField;
	JLabel newBookISBNLabel;
	JTextField newBookISBNField;
	JButton addBookButton;
	
    private static Library library;
    
    @Override // java.awt.event.ActionListener
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
        	fillSearchResults(searchField.getText());
        }
        if (event.getSource() == submitLoginButton) {
        	//UserDataStore.getInstance().isLoginCorrect(usernameField.getText(), passwordField.getText());
        	if (UserTest.login(usernameField.getText(), passwordField.getText())) {
        		userSignedIn = true;
        		currentUser = usernameField.getText();
        		
        		JOptionPane.showMessageDialog(frame, "Sign in success!");
        		
        		// go back to make screen after sign in
        		createSearchGui();
        	}
        	else 
        	{
        		JOptionPane.showMessageDialog(frame, "Incorrect username or password!");
        	}
        	
        }
        if (event.getSource() == submitUserRegistertationButton) {
        	UserTest.AddUser("u", registerUsernameField.getText(), registerPasswordField.getText());
        	userSignedIn = true;
        	currentUser = registerUsernameField.getText();
        	
        	JOptionPane.showMessageDialog(frame, "User account created!");
        	
        	// go back to main screen after user account creation
        	createSearchGui();
        }
        if (event.getSource() == addBookButton) {
        	if (!newBookTitleField.getText().equals("") && !newBookAuthorField.getText().equals("") && newBookISBNField.getText().length() > 8) {
        		try {
					library.addNewBook(newBookTitleField.getText(), newBookAuthorField.getText(), newBookISBNField.getText());
					JOptionPane.showMessageDialog(frame, "Book aded!");
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else {
        		JOptionPane.showMessageDialog(frame, "Invalid values entered!");
        	}
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        createGui();
        
        // default to search gui
        createSearchGui();
    }

    private void addCheckedOutBooks(Book book) {
        panel.add(Box.createVerticalStrut(10));
       
        JLabel bookInfo = new JLabel();
        bookInfo.setText(book.getTitle() + "  by " + book.getAuthor());
        System.out.println("checked out: " + book.getTitle());
        panel.add(bookInfo);
        
        JButton button = new JButton("Return");
        Dimension size = new Dimension(130, 30);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// use blank as second parameter to have book returned
				library.updateCheckoutStatus(book, "");
				
				JOptionPane.showMessageDialog(frame, book.getTitle() + " returned!");
				createMyAccountGui();
			}
		});
            
        
		panel.add(button);
        
        panel.revalidate();
    }
    
    private void addSearchResult(Book book) {
    	System.out.println("Adding search result");
        panel.add(Box.createVerticalStrut(10));
       
        JLabel bookInfo = new JLabel();
        bookInfo.setText(book.getTitle() + "  by " + book.getAuthor());
        panel.add(bookInfo);
                
        if (book.getCheckedOutStatus() == false) {
            JButton button = new JButton("Checkout");
            Dimension size = new Dimension(130, 30);
            button.setMaximumSize(size);
            button.setMinimumSize(size);
            button.setPreferredSize(size);
            
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (userSignedIn) {					
						library.updateCheckoutStatus(book, currentUser);
						
						JOptionPane.showMessageDialog(frame, book.getTitle() + " checked out!");
						
						// go back to search
						createSearchGui();
						fillSearchResults(searchField.getText());
					}
					else {
						JOptionPane.showMessageDialog(frame, "Please sign in first");

					}
				}
			});
            
            panel.add(button);
        }
        else {
        	JLabel notAvailable = new JLabel("Not Available");
        	notAvailable.setFont(new Font("Serif", Font.BOLD, 18));
        	panel.add(notAvailable);
        }
        
        panel.revalidate();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        
		searchLabel = new JLabel("Search: ");
		searchField = new JTextField(20);
        
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
		
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }
    
    private JPanel createRegistrationPanel() {
    	JPanel registrationPanel = new JPanel();
    	
        LayoutManager mgr = new BoxLayout(registrationPanel, BoxLayout.Y_AXIS);        
        registrationPanel.setLayout(mgr);
        

        JLabel registerTitle = new JLabel("New User Registration", SwingConstants.CENTER);
        registerTitle.setFont(new Font("Serif", Font.BOLD, 18));
        registerTitle.setBounds(100, 8, 70, 80);

    	
    	firstNameLabel = new JLabel("First Name: ");
    	firstNameField = new JTextField(30);
    	firstNameField.setMaximumSize( firstNameField.getPreferredSize() );
    	//firstNameField.setMargin(new Insets(10, 5, 10, 10));
    	//firstNameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    	
    	lastNameLabel = new JLabel("Last Name: ");
    	lastNameField = new JTextField(30);
    	lastNameField.setMaximumSize( lastNameField.getPreferredSize());
    	
    	streetAddressLabel = new JLabel("Street Address: ");
    	streetAddressField = new JTextField(30);
    	streetAddressField.setMaximumSize( streetAddressField.getPreferredSize());
    	
    	cityLabel = new JLabel("City");
    	cityField = new JTextField(30);
    	cityField.setMaximumSize( cityField.getPreferredSize());
    	
    	stateLabel = new JLabel("State");
    	stateField = new JTextField(30);
    	stateField.setMaximumSize( stateField.getPreferredSize());
    	
    	zipCodeLabel = new JLabel("Zip Code");
    	zipCodeField = new JTextField(30);
    	zipCodeField.setMaximumSize( zipCodeField.getPreferredSize());
    	
    	emailLabel = new JLabel("Email");
    	emailField = new JTextField(30);
    	emailField.setMaximumSize( emailField.getPreferredSize());
    	
    	registerUsernameLabel = new JLabel("Username");
    	registerUsernameField = new JTextField(30);
    	registerUsernameField.setMaximumSize( registerUsernameField.getPreferredSize());
    	
    	registerPasswordLabel = new JLabel("Password");
    	registerPasswordField = new JPasswordField(30);
    	registerPasswordField.setMaximumSize( registerPasswordField.getPreferredSize());
    	

    	submitUserRegistertationButton = new JButton("Submit");
    	submitUserRegistertationButton.addActionListener(this);
    	
    	registrationPanel.add(registerTitle);
    	registrationPanel.add(firstNameLabel);
    	registrationPanel.add(firstNameField);
    	registrationPanel.add(lastNameLabel);
    	registrationPanel.add(lastNameField);
    	registrationPanel.add(streetAddressLabel);
    	registrationPanel.add(streetAddressField);
    	registrationPanel.add(cityLabel);
    	registrationPanel.add(cityField);
    	registrationPanel.add(stateLabel);
    	registrationPanel.add(stateField);
    	registrationPanel.add(zipCodeLabel);
    	registrationPanel.add(zipCodeField);
    	registrationPanel.add(emailLabel);
    	registrationPanel.add(emailField);
    	registrationPanel.add(registerUsernameLabel);
    	registrationPanel.add(registerUsernameField);
    	registrationPanel.add(registerPasswordLabel);
    	registrationPanel.add(registerPasswordField);
    	registrationPanel.add(submitUserRegistertationButton);
    	
    	return registrationPanel;
    }
    
    private JPanel createMyAccountPanel() {
    	JPanel myAccountPanel = new JPanel();
    	
        LayoutManager mgr = new BoxLayout(myAccountPanel, BoxLayout.Y_AXIS);        
        myAccountPanel.setLayout(mgr);
        

        JLabel myAccountTitle = new JLabel("My Account", SwingConstants.CENTER);
        myAccountTitle.setFont(new Font("Serif", Font.BOLD, 18));
        myAccountTitle.setBounds(100, 8, 70, 80);
        
        JLabel myCheckedOutBookLabel = new JLabel("My checked out books:");
        
        
        myAccountPanel.add(myAccountTitle);
        myAccountPanel.add(myCheckedOutBookLabel);
        
        
        return myAccountPanel;
    }
    
    private JPanel createAddRemovePanel() {
    	JPanel addRemovePanel = new JPanel();
    	
        LayoutManager mgr = new BoxLayout(addRemovePanel, BoxLayout.Y_AXIS);        
        addRemovePanel.setLayout(mgr);
        
        JLabel addRemoveTitle = new JLabel("Add Book", SwingConstants.CENTER);
        addRemovePanel.setFont(new Font("Serif", Font.BOLD, 18));
        addRemovePanel.setBounds(100, 8, 70, 80);
        
    	newBookTitleLabel = new JLabel("Book Title");
    	newBookTitleField = new JTextField(30);
    	newBookTitleField.setMaximumSize( newBookTitleField.getPreferredSize());
    	
    	newBookAuthorLabel = new JLabel("Book Author");
    	newBookAuthorField = new JTextField(30);
    	newBookAuthorField.setMaximumSize( newBookAuthorField.getPreferredSize());
    	
    	newBookISBNLabel = new JLabel("Book ISBN");
    	newBookISBNField = new JTextField(30);
    	newBookISBNField.setMaximumSize( newBookISBNField.getPreferredSize());
    	
    	addBookButton = new JButton("Add Book");
    	addBookButton.addActionListener(this);
    	
        addRemovePanel.add(addRemoveTitle);
        addRemovePanel.add(newBookTitleLabel);
        addRemovePanel.add(newBookTitleField);
        addRemovePanel.add(newBookAuthorLabel);
        addRemovePanel.add(newBookAuthorField);
        addRemovePanel.add(newBookISBNLabel);
        addRemovePanel.add(newBookISBNField);
        addRemovePanel.add(addBookButton);
        
        return addRemovePanel;
    }
    
    private JPanel createLoginPanel() {
    	JPanel loginPanel = new JPanel();
    	    	
    	if (userSignedIn) {
    		JLabel loggedIn = new JLabel(currentUser + " logged in");
    		loginPanel.add(loggedIn);
    	} else {
			usernameLabel = new JLabel("Username: ");
			usernameField = new JTextField(30);
			
			passwordLabel = new JLabel("Password: ");
			passwordField = new JPasswordField(30);
			
			submitLoginButton = new JButton("Login");
			submitLoginButton.addActionListener(this);
			
			loginPanel.add(usernameLabel);
			loginPanel.add(usernameField);
			loginPanel.add(passwordLabel);
			loginPanel.add(passwordField);
			loginPanel.add(submitLoginButton);
    	}
    	
    	return loginPanel;
    }

    private void createGui() {
        frame = new JFrame("Library System");
    	pane = frame.getContentPane();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    
        frame.setLocationByPlatform(true);
        frame.setJMenuBar(makeMenus());
    }
    
    private void createSearchGui() {
    	pane.removeAll();
        pane.add(createSearchPanel(), BorderLayout.PAGE_START);
        pane.add(createSearchResultsPanel(), BorderLayout.CENTER);
        pane.add(createLoginPanel(), BorderLayout.PAGE_END);
        pane.revalidate();
        frame.pack();
        frame.setVisible(true);
    }
    
    private void createRegistrationGui() {
    	pane.removeAll();       
    	pane.add(createRegistrationPanel(), BorderLayout.PAGE_START);
    	pane.add(createLoginPanel(), BorderLayout.PAGE_END);
    	pane.revalidate();
    	frame.pack();
    	frame.setVisible(true);
    }
    
	private void createMyAccountGui() {
		if (userSignedIn) {
	    	pane.removeAll();       
	    	pane.add(createMyAccountPanel(), BorderLayout.PAGE_START);
	        pane.add(createCheckedoutBooksPanel(), BorderLayout.CENTER);
	    	pane.add(createLoginPanel(), BorderLayout.PAGE_END);
	    	pane.revalidate();
	        showCheckedOutBooks();
	    	frame.pack();
	    	frame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(frame, "Please sign in first!");
		}
	}
	
	private void createAddRemoveGui() {
		if (userSignedIn && UserTest.isAdmin) {
	    	pane.removeAll();       
	    	pane.add(createAddRemovePanel(), BorderLayout.PAGE_START);
	    	pane.add(createLoginPanel(), BorderLayout.PAGE_END);
	    	pane.revalidate();
	    	frame.pack();
	    	frame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(frame, "Must be signed in as an admin!");
		}
	}

    private JScrollPane createSearchResultsPanel() {
        panel = new JPanel();
        LayoutManager mgr = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(mgr);
        
        JScrollPane scrollPane = new JScrollPane(panel, 
                                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        return scrollPane;
    }
    
    private JScrollPane createCheckedoutBooksPanel() {
        panel = new JPanel();
        LayoutManager mgr = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(mgr);
        
        JScrollPane scrollPane = new JScrollPane(panel, 
                                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        return scrollPane;
    }

	private void fillSearchResults(String searchEntry) {
		ArrayList<Book> searchListFound = library.findBooks(searchEntry);
        System.out.println("Searches found: " + searchListFound.size());
		if (searchListFound.size() > 0) {
			String results = "";
			
			for (int i = 0; i < searchListFound.size(); i++) {
				results += searchListFound.get(i).getTitle() + "\n";
				addSearchResult(searchListFound.get(i));
			}
			
			System.out.println("Found: " + results);
		
		}
		else {
            JOptionPane.showMessageDialog(frame, "No results found for: " + searchEntry);
		}
	}
	
	private void showCheckedOutBooks() {
		ArrayList<Book> checkedOutBooks = library.findCheckedOutBooks(currentUser);
        
		if (checkedOutBooks.size() > 0) {
			String results = "";
			
			for (int i = 0; i < checkedOutBooks.size(); i++) {
				results += checkedOutBooks.get(i).getTitle() + "\n";
				addCheckedOutBooks(checkedOutBooks.get(i));
			}
			
			System.out.println("Found: " + results);
		
		}
	}
    
	// createe menu bar
	private JMenuBar makeMenus() {
		ActionListener listener = new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				String cmd = evt.getActionCommand();
				
				// check for each menu option
				if (cmd.equals("Search for books"))
					createSearchGui();
				else if (cmd.equals("New user registration"))
					createRegistrationGui();
				else if (cmd.equals("My Account"))
					createMyAccountGui();
				else if (cmd.equals("Add / Remove Books"))
					createAddRemoveGui();
			}
			
		};
		
		JMenu mainMenu = new JMenu("Menu");
		
		// search
		JMenuItem searchCmd = new JMenuItem("Search for books");
		searchCmd.addActionListener(listener);
		mainMenu.add(searchCmd);
		
		// new user registration
		JMenuItem registerCmd = new JMenuItem("New user registration");
		registerCmd.addActionListener(listener);
		mainMenu.add(registerCmd);
		
		// view my account
		JMenuItem myAccountCmd = new JMenuItem("My Account");
		myAccountCmd.addActionListener(listener);
		mainMenu.add(myAccountCmd);
		
		// add / remove books
		JMenuItem addRemoveCmd = new JMenuItem("Add / Remove Books");
		addRemoveCmd.addActionListener(listener);
		mainMenu.add(addRemoveCmd);
		
		// build menu bar and add main menu to it
		JMenuBar bar = new JMenuBar();
		bar.add(mainMenu);
		return bar;
	}
	
    public static void main(String[] args) {
    	library = new Library();
        EventQueue.invokeLater(new LibraryGUI());        
	}
  
}
