import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 * @author Robert Miller
 * 09/25/2022
 * CMSC 495/6381
 * LibraryGUI class.
 *
 */

public class LibraryGUI extends JPanel implements ActionListener{

	JButton loginButton;
	
	JLabel usernameLabel;
	JTextField usernameField;
	JLabel passwordLabel;
	JTextField passwordField;
	JLabel searchLabel;
	JTextField searchField;
	JTextArea log;
	
	// constructor
	public LibraryGUI() {
		super(new BorderLayout());
		
		usernameLabel = new JLabel("Username: ");
		usernameField = new JTextField(30);
		
		passwordLabel = new JLabel("Password: ");
		passwordField = new JTextField(30);
		
		searchLabel = new JLabel("Search: ");
		searchField = new JTextField(20);
		
		JPanel loginPanel = new JPanel();
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		
		JPanel searchPanel = new JPanel();
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);		

        JPanel outputPanel = new JPanel();
        //outputPanel.add(outputLabel);
        //outputPanel.add(outputField);
        
        add(loginPanel, BorderLayout.PAGE_START);
        add(searchPanel, BorderLayout.WEST);
        add(outputPanel, BorderLayout.CENTER);
                
        
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Library System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new LibraryGUI());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == loginButton) {
        	
        }
	}
}
