/**
 * 
 * @author Robert Miller
 * 09/27/2022
 * CMSC 495/6381
 * LibraryGUI class. Currently displays search panel and outputs search results. Allows for basic checkout. 
 *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class LibraryGUI implements ActionListener, Runnable {

    private JFrame  frame;
    private JPanel  panel;
    
	private JLabel searchLabel;
	private JTextField searchField;
    private JButton searchButton;
    
	JButton loginButton;
	
	JLabel usernameLabel;
	JTextField usernameField;
	JLabel passwordLabel;
	JTextField passwordField;
    
    private static Library library;
    
    @Override // java.awt.event.ActionListener
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
        	fillSearchResults(searchField.getText());
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        createGui();
        
        // default to search gui
        createSearchGui();
    }

    private void addSearchResult(Book book) {
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
					book.setCheckedOutStatus(true);
					JOptionPane.showMessageDialog(frame, book.getTitle() + " checked out!");
				}
			});
            
            panel.add(button);
        }
        else {
        	JLabel notAvailable = new JLabel("Not Available");
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
    
    private JPanel createLoginPanel() {
    	JPanel loginPanel = new JPanel();
    	
		usernameLabel = new JLabel("Username: ");
		usernameField = new JTextField(30);
		
		passwordLabel = new JLabel("Password: ");
		passwordField = new JTextField(30);
		
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
    	
    	return loginPanel;
    }

    private void createGui() {
        frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);      
        frame.setJMenuBar(makeMenus());
    }
    
    private void createSearchGui() {
        frame.add(createSearchPanel(), BorderLayout.PAGE_START);
        frame.add(createSearchResultsPanel(), BorderLayout.CENTER);
        frame.add(createLoginPanel(), BorderLayout.PAGE_END);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
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

	private void fillSearchResults(String searchEntry) {
		ArrayList<Book> searchListFound = library.findBooks(searchEntry);
        
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
    
	// createe menu bar
	private JMenuBar makeMenus() {
		ActionListener listener = new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				String cmd = evt.getActionCommand();
				
				// check for each menu option
				if (cmd.equals("Search for books"))
					createSearchGui();
			}
			
		};
		
		JMenu mainMenu = new JMenu("Menu");
		
		// load media objects menu item
		JMenuItem searchCmd = new JMenuItem("Search for books");
		searchCmd.addActionListener(listener);
		mainMenu.add(searchCmd);
		
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