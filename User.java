
/**
 * File: User.java
 * @author Group 4(Andrew J. Martino, Kyle Pope, Rob Miller)
 * Create Date: 9/27/2022
 * Purpose: SubClass of the user class to get user information
 * 
 * Revision History:
 * 9/27/2022
 * Andrew - Add the method from the Pseudocode and implement a password check.
 * 
 * 10/4/2022 
 * add ChangePassword to the User Class 
 * 
 */
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import static java.lang.System.exit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
//import java.sql.SQLException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class User extends JPanel{
    
    private String userName;
    private String password;
    private UserType typeUser;
    
    private final JPasswordField currPass = new JPasswordField();
    private final JPasswordField newPass = new JPasswordField();
    private final JPasswordField confText = new JPasswordField();
    private final JTextField user = new JTextField();
    
    private final JTextField currName = new JTextField();
    private final JTextField newName = new JTextField();
    private final JTextField confName = new JTextField();
    
    private final JButton logout = new JButton();
    
    public User(){
        initalize();
        initalize2();
        
        
    }
    
    private void initalize2(){
        final JLabel header2 = new JLabel("Change UserName");
        final JPanel headerLib2 = new JPanel();
        headerLib2.add(headerLib2);
        
        final JPanel dragonUser = new JPanel();
        final JLabel currUserA = new JLabel("Current UserName: ");
        final JLabel newUserA = new JLabel("New UserName: ");
        final JLabel confUserA = new JLabel("Confirm New UserName: ");
        final JButton buttonConfirm2 = new JButton("Confirm");
        
        buttonConfirm2.addActionListener(new ConfirmButton2());
        
        final GridBagConstraints dragon = new GridBagConstraints();
        dragon.insets = new Insets(8,8,8,8);
        dragonUser.setLayout(new GridBagLayout());
        
        
        dragon.gridx = 0;
        dragon.gridy = 0;
        dragonUser.add(currUserA, dragon);
        
        dragon.gridx = 0;
        dragon.gridy = 1;
        dragonUser.add(newUserA, dragon);
        
        dragon.gridx = 0;
        dragon.gridy = 2;
        dragonUser.add(confUserA, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 0;
        currName.setColumns(15);
        dragonUser.add(currName, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 1;
        newName.setColumns(15);
        dragonUser.add(newName, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 2;
        confName.setColumns(15);
        dragonUser.add(confName, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 3;
        dragonUser.add(buttonConfirm2, dragon);
        
        final JPanel dragonLibrary2 = new JPanel(new BorderLayout());
        dragonLibrary2.add(headerLib2, BorderLayout.PAGE_START);
        dragonLibrary2.add(dragonUser, BorderLayout.CENTER);
        logout.addActionListener(new ConfirmButton2(){
          @Override
          public void actionPerformed(ActionEvent e){
              System.exit(0);
          } 
        });
        logout.setBounds(348, 12, 89, 23);
        dragonLibrary2.add(logout);
        add(dragonLibrary2);
        
        
    }
    
    
    private class ConfirmButton2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                Connection conn = DriverManager.
                        getConnection("jdbc::sqlite::resource:ChangePassword");
                Statement stat = conn.createStatement();
                String dragonCurr2 = currName.getText();
                String dragonNew2 = newName.getText();
                String dragonConf2 = confName.getText();
                ResultSet rs = null;
                if(dragonNew2.equals(dragonConf2)){
                    stat.execute("update ChangePassword set User = "
                            + "'"+dragonNew2+"' where User = "
                                    + "'"+dragonCurr2+"'");
                    JOptionPane.showMessageDialog(null, "Change is successful");
                }else{
                    JOptionPane.showMessageDialog(null, "wrong username");
                }
            }catch(SQLException a1){
                a1.printStackTrace();
            }
        }
        
    }
    
    public void initalize(){
        final JLabel header = new JLabel("Change Password");
        final JPanel headerLib = new JPanel();
        headerLib.add(header);
        
        final JPanel userPanel = new JPanel();
        final JLabel currUser = new JLabel("UserName: ");
        final JLabel currPassA = new JLabel("Current Password: ");
        final JLabel newPassA = new JLabel("New Password: ");
        final JLabel confPass = new JLabel("Confirm New Password: ");
        final JButton buttonConfirm = new JButton("Confirm");
        
        buttonConfirm.addActionListener(new ConfirmButton());
        
        final GridBagConstraints dragon = new GridBagConstraints();
        dragon.insets = new Insets(8,8,8,8);
        userPanel.setLayout(new GridBagLayout());
        
        dragon.gridx = 0;
        dragon.gridy = -1;
        userPanel.add(currUser, dragon);
        
        dragon.gridx = 0;
        dragon.gridy = 0;
        userPanel.add(currPassA, dragon);
        
        dragon.gridx = 0;
        dragon.gridy = 1;
        userPanel.add(newPassA, dragon);
        
        dragon.gridx = 0;
        dragon.gridy = 2;
        userPanel.add(confPass, dragon);
        
        dragon.gridx = -1;
        dragon.gridy = -2;
        user.setColumns(15);
        userPanel.add(user, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 0;
        currPass.setColumns(15);
        userPanel.add(currPass, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 1;
        newPass.setColumns(15);
        userPanel.add(newPass, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 2;
        confText.setColumns(15);
        userPanel.add(confText, dragon);
        
        dragon.gridx = 1;
        dragon.gridy = 3;
        userPanel.add(buttonConfirm, dragon);
        
        
        final JPanel dragonLibrary = new JPanel(new BorderLayout());
        dragonLibrary.add(headerLib, BorderLayout.PAGE_START);
        dragonLibrary.add(userPanel, BorderLayout.CENTER);
        add(dragonLibrary);
        
    }
    
    private class ConfirmButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                Connection conn = DriverManager.
                        getConnection("jdbc::sqlite::resource:ChangePassword");
                Statement stat = conn.createStatement();
                String dragonUser = user.getText();
                String dragonCurr = new String(currPass.getPassword());
                String dragonNew = new String(newPass.getPassword());
                String dragonConf = new String(confText.getPassword());
                ResultSet rs= null;
                if(dragonNew.equals(dragonConf)){
                    stat.execute("update ChangePassword set Password = "
                            + "'"+dragonNew+"' where User = '"+dragonUser+"'");
                    JOptionPane.showMessageDialog(null, "Change is successful");
                }else{
                    JOptionPane.showMessageDialog(null, "wrong password");
                }
            }catch(HeadlessException | SQLException e1){
                e1.printStackTrace();
            }
            
        }
        
    }
  
    public void getUserType(){
       
    }
    
    public String login(String userName, String password){
        return null;
    }
    
    
   /* public String changePassword(String password){
        if(password.length() > 7){
           
        }
        else{
            System.out.println("Too small");
            return password;
        }
        return password;
    }
    
    public static boolean checkPass(String password){
        boolean hasNum = false;
        boolean hasCap = false;
        boolean lowNum = false;
        char d;
        
       for(int i = 0; i < password.length(); i++){
           d = password.charAt(i);
           if(Character.isDigit(d)){
               hasNum = true; 
           }else if(Character.isUpperCase(d)){
               hasCap = true;
           }else if(Character.isLowerCase(d)){
               lowNum = true;
           }
           if(hasNum && hasCap && lowNum){
               return true;
           }
    
       }
       return false;
    }*/
}