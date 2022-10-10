
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * File: ChangePassword.java
 * @author Group 4(Andrew J. Martino, Kyle Pope, Rob Miller)
 * Create Date: 10/9/2022
 * Purpose: SubClass of the ChangePassword class to get user password
 * information. The JLabels and JPasswordFields prompt the user to input
 * password information, including old and new.
 * 
 * Revision History:
 * Separate from the User class into ChangePassword and ChangeUserName
 * 
 * Add all field to make change password work
 * Add Commits 
*/
public class ChangePassword extends JPanel {
    //JPasswordField for the User to input the user data 
    private final JPasswordField currPass = new JPasswordField();
    private final JPasswordField newPass = new JPasswordField();
    private final JPasswordField confText = new JPasswordField();
    private final JTextField user = new JTextField();
    private final JButton logout = new JButton();
    
    //ChangePassword Constructor 
    public ChangePassword(){
        //Creating Header Panel
        final JLabel header = new JLabel("Change Password");
        final JPanel headerLib = new JPanel();
        headerLib.add(header);
        //Creating Panel and Components for the GUi
        final JPanel userPanel = new JPanel();
        final JLabel currUser = new JLabel("UserName: ");
        final JLabel currPassA = new JLabel("Current Password: ");
        final JLabel newPassA = new JLabel("New Password: ");
        final JLabel confPass = new JLabel("Confirm New Password: ");
        final JButton buttonConfirm = new JButton("Confirm");
        //The confirm button
        buttonConfirm.addActionListener(new ConfirmButton());
        //Editing Panel
        final GridBagConstraints dragon = new GridBagConstraints();
        dragon.insets = new Insets(8,8,8,8);
        userPanel.setLayout(new GridBagLayout());
        //Enter UserName label
        dragon.gridx = -1;
        dragon.gridy = -1;
        userPanel.add(currUser, dragon);
        //Current Password label
        dragon.gridx = 0;
        dragon.gridy = 0;
        userPanel.add(currPassA, dragon);
        //New Password label
        dragon.gridx = 0;
        dragon.gridy = 1;
        userPanel.add(newPassA, dragon);
        //Confirm Password label
        dragon.gridx = 0;
        dragon.gridy = 2;
        userPanel.add(confPass, dragon);
        //Username Textfield
        dragon.gridx = -1;
        dragon.gridy = -2;
        user.setColumns(15);
        userPanel.add(user, dragon);
        //Current Password TextField
        dragon.gridx = 1;
        dragon.gridy = 0;
        currPass.setColumns(15);
        userPanel.add(currPass, dragon);
        //New Current Password TextField
        dragon.gridx = 1;
        dragon.gridy = 1;
        newPass.setColumns(15);
        userPanel.add(newPass, dragon);
        //Confirm Password TextField
        dragon.gridx = 1;
        dragon.gridy = 2;
        confText.setColumns(15);
        userPanel.add(confText, dragon);
        //Confirm Button
        dragon.gridx = 1;
        dragon.gridy = 3;
        userPanel.add(buttonConfirm, dragon);
        //Creating the Full Panel and adding all panels
        final JPanel dragonLibrary = new JPanel(new BorderLayout());
        dragonLibrary.add(headerLib, BorderLayout.PAGE_START);
        dragonLibrary.add(userPanel, BorderLayout.CENTER);
        //Exit the Program Button
        logout.addActionListener(new ConfirmButton(){
          @Override
          public void actionPerformed(ActionEvent e){
              System.exit(0);
          } 
        });
        logout.setBounds(348, 12, 89, 23);
        dragonLibrary.add(logout);
        add(dragonLibrary);
    }//end of the constructor
    
     //Class: Confirm Button Listener 
    private class ConfirmButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{//Connecting to sql Database
                Connection conn = DriverManager.
                        getConnection("jdbc::sqlite::resource:ChangePassword");
                Statement stat = conn.createStatement();
                //Store input
                String dragonUser = user.getText();
                String dragonCurr = new String(currPass.getPassword());
                String dragonNew = new String(newPass.getPassword());
                String dragonConf = new String(confText.getPassword());
                ResultSet rs= null;
                //Confirm new Password
                if(dragonNew.equals(dragonConf)){
                    stat.execute("update ChangePassword set Password = "
                            + "'"+dragonNew+"' where User = '"+dragonUser+"'");
                    //Correct Change Password
                    JOptionPane.showMessageDialog(null, "Change is successful");
                }else{//Mismatch New and Confirm username
                    JOptionPane.showMessageDialog(null, "wrong password");
                }
                
            }catch(HeadlessException | SQLException e1){
                //If the SQL doesn't work 
                JOptionPane.showMessageDialog(null, "SQL doesn't work");
            }
            
        }
        
    }
}//end of ChangePassword