//File: UserTest.Java
//Author: Kyle Pope
//Date: 10/4/2022
//Purpose: Create a class to log in

// Updated 10//09/22 by Robert Miller
// Added check to see if user text file exists, and if it doesn't, create one with an admin user

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UserTest {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/users.txt";
    private static Scanner file;
    static int rows;
    public static boolean isAdmin = false;
    
    // Method to pull the user account details from the .txt file
    public static String[][] UserInfo() {
    	// create users text if none exists
    	File f = new File(FILE_PATH);
    	if(!f.exists()) { 
    	    createUsersText();
    	}
        // create a 2d arraylist to store login information of variable length
        ArrayList<ArrayList<String>> loginInfo = new ArrayList<>();
        try {
            file = new Scanner(new File(FILE_PATH));

            while (file.hasNextLine()) {
                ArrayList<String> line = new ArrayList<>();
                final String nextLine = file.nextLine();
                final String[] items = nextLine.split(" ");
                for (int i = 0; i < items.length; i++) {
                    line.add(items[i]);
                }
                loginInfo.add(line);
                rows++;
                Arrays.fill(items, null); // to clear out the 'items' array
            }
            String[][] stringArray = loginInfo.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
            //System.out.print("String array" + stringArray[1][1]);
            return stringArray;
        } catch (FileNotFoundException e) {
            System.out.println("File was not found, unable to create");
            return null;
        }
    }

    // Method to add a new user to the .txt document, ensure accountType is a for admin, or u for user
    public static void AddUser(String accountType, String newName, String newPass) {
    	String[][] userInfo = UserInfo();
    	
        try {
            // ensures it is a valid account type
            if (accountType.equals("a") || accountType.equals("u")) {
                FileWriter fw = new FileWriter(FILE_PATH, true);
                // makes a new line 
                fw.write(System.getProperty("line.separator"));
                // then enters the new accounttype/username/password
                fw.write(accountType + " " + newName + " " + newPass);
                fw.close();
            } else {
                System.out.println("Ensure you enter a proper account type.");
            }
        } catch (IOException e) {
            System.out.println("File was not found, ensure the file path is correct");
            //createUsersText();
        }
    }
    
    public static boolean login(String username, String password) {
        String[][] userInfo = UserInfo();
        //System.out.println("User" + userInfo[0][1]);
        int row = userInfo.length;
        int count = 0;
        for (int i = 0; i < row; i++) {
            // this pulls the account types
        	
            String account = userInfo[i][0];
            if (account.equals("a"))
            	isAdmin = true;
            // this pulls the username
            String ucheck = userInfo[i][1];
            // this pulls the password
            String pCheck = userInfo[i][2];
            // if username matches password then success
            if (ucheck.equals(username) && pCheck.equals(password)) {
                return true;
            } else {
                count++;
            }
            // if the end of the file is reached without a login then the login fails
            if (count == row) {
                System.out.println("Login Failed");            }
        }
        
        return false;
    }
    
    public static void createUsersText() {
    	
        PrintWriter writer = null;
		try {
			writer = new PrintWriter(FILE_PATH, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        writer.print("a admin admin");
        writer.write(System.getProperty("line.separator"));
        writer.print("u user user");
        writer.close();
    }
    
    public static void updatePassword(String username, String oldPassword, String newPassword) {
        String[][] userInfo = UserInfo();
        //System.out.println("User" + userInfo[0][1]);
        int row = userInfo.length;
        int count = 0;
        for (int i = 0; i < row; i++) {
            // this pulls the account types
        	
            // this pulls the username
            String ucheck = userInfo[i][1];
            // this pulls the password
            String pCheck = userInfo[i][2];
            // if username matches password then success
            if (ucheck.equals(username) && pCheck.equals(oldPassword)) {
            	
            } else {
                count++;
            }
            // if the end of the file is reached without a login then the login fails
            if (count == row) {
                System.out.println("Login Failed");            }
        }
        
        //return false;
    }
    
    // main method that asks for password/username and logs in the user if successful.
    public static void main(String[] args) throws FileNotFoundException {
    	/*
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your username:");
        String username = scan.nextLine();
        System.out.println("Please enter your password:");
        String password = scan.nextLine();
        String[][] userInfo = UserInfo();
        int row = userInfo.length;
        int count = 0;
        for (int i = 0; i < row; i++) {
            // this pulls the account types
            String accountType = userInfo[i][0];
            // this pulls the username
            String ucheck = userInfo[i][1];
            // this pulls the password
            String pCheck = userInfo[i][2];
            // if username matches password then success
            if (ucheck.equals(username) && pCheck.equals(password)) {
                System.out.println("Login Success");
                break;
            } else {
                count++;
            }
            // if the end of the file is reached without a login then the login fails
            if (count == row) {
                System.out.println("Login Failed");
            }
        }
        */
    }
}
