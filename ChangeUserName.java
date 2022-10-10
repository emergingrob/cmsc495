
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTextField;

/**
 * File: ChangeUserName.java
 * @author Group 4(Andrew J. Martino, Kyle Pope, Rob Miller)
 * Create Date: 10/9/2022
 * Purpose: SubClass of the ChangeUserName class to get information for Member.
 * The JLabels and JTextField prompt the user to input password information, 
 * including old and new.
 * 
 * Revision History
 * 10/9/2022
 * Separate from the User class into ChangeUserName and ChangePassword
 * 
 * Add all field to make change username work
 * Add Commits 
*/
public class ChangeUserName extends JPanel {
    //TextField for the User to input the user data 
    private final JTextField currName = new JTextField();
    private final JTextField newName = new JTextField();
    private final JTextField confName = new JTextField();
    private final JButton logout = new JButton();
    
    //The Constructor for the JPanel
    public ChangeUserName(){
        //Creating Header Panel
        final JLabel header2 = new JLabel("Change UserName");
        final JPanel headerLib2 = new JPanel();
        headerLib2.add(headerLib2);
        //Creating Panel and Components for the GUi
        final JPanel dragonUser = new JPanel();
        final JLabel currUserA = new JLabel("Current UserName: ");
        final JLabel newUserA = new JLabel("New UserName: ");
        final JLabel confUserA = new JLabel("Confirm New UserName: ");
        final JButton buttonConfirm2 = new JButton("Confirm");
        
        buttonConfirm2.addActionListener(new ConfirmButton2());
        //Editing Panel
        final GridBagConstraints dragon = new GridBagConstraints();
        dragon.insets = new Insets(8,8,8,8);
        dragonUser.setLayout(new GridBagLayout());
        //Current UserName label
        dragon.gridx = 0;
        dragon.gridy = 0;
        dragonUser.add(currUserA, dragon);
        //New Username label
        dragon.gridx = 0;
        dragon.gridy = 1;
        dragonUser.add(newUserA, dragon);
        //Confirm New Password Label
        dragon.gridx = 0;
        dragon.gridy = 2;
        dragonUser.add(confUserA, dragon);
        //Current UserName TextField
        dragon.gridx = 1;
        dragon.gridy = 0;
        currName.setColumns(15);
        dragonUser.add(currName, dragon);
        //New UserName TextField
        dragon.gridx = 1;
        dragon.gridy = 1;
        newName.setColumns(15);
        dragonUser.add(newName, dragon);
        //Confirm New UserName TextField
        dragon.gridx = 1;
        dragon.gridy = 2;
        confName.setColumns(15);
        dragonUser.add(confName, dragon);
        //Confirm Button 
        dragon.gridx = 1;
        dragon.gridy = 3;
        dragonUser.add(buttonConfirm2, dragon);
        
        //Creating the Full Panel and adding all panels
        final JPanel dragonLibrary2 = new JPanel(new BorderLayout());
        dragonLibrary2.add(headerLib2, BorderLayout.PAGE_START);
        dragonLibrary2.add(dragonUser, BorderLayout.CENTER);
        //Exit the Program Button
        logout.addActionListener(new ConfirmButton2(){
          @Override
          public void actionPerformed(ActionEvent e){
              System.exit(0);
          } 
        });
        logout.setBounds(348, 12, 89, 23);
        dragonLibrary2.add(logout);
        add(dragonLibrary2);
        
        
    }//end of the constructor
    
    //Class: Confirm Button Listener   
    private class ConfirmButton2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{//Connecting to sql Database
                Connection conn = DriverManager.
                        getConnection("jdbc::sqlite::resource:ChangePassword");
                Statement stat = conn.createStatement();
                //Store input
                String dragonCurr2 = currName.getText();
                String dragonNew2 = newName.getText();
                String dragonConf2 = confName.getText();
                ResultSet rs = null;
                //Confirm new username 
                if(dragonNew2.equals(dragonConf2)){
                    stat.execute("update ChangePassword set User = "
                            + "'"+dragonNew2+"' where User = "
                                    + "'"+dragonCurr2+"'");
                    //Correct Change UserName
                    JOptionPane.showMessageDialog(null, "Change is successful");
                }else{
                    //Mismatch New and Confirm username
                    JOptionPane.showMessageDialog(null, "wrong username");
                }
            }catch(SQLException a1){
                JOptionPane.showMessageDialog(null, "SQL doesn't work");
            }
        }
        
    }
}//end of ChangeUserName.java 