//File: UserTest.Java
//Author: Kyle Pope
//Date: 10/4/2022
//Purpose: Create a class to log in

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class UserTest {

    // ENSURE YOU CHANGE THE FILE LOCATION TO MATCH THE USER'S COMPUTER PATH
    private static final String FILE_PATH = "H:\\test.txt";
    private static Scanner file;
    static int rows;

    // Method to pull the user account details from the .txt file
    public static String[][] UserInfo() {
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
            System.out.print("String array" + stringArray[1][1]);
            return stringArray;
        } catch (FileNotFoundException e) {
            System.out.println("File was not found, ensure the file path is correct");
            return null;
        }
    }

    // Method to add a new user to the .txt document, ensure accountType is a for admin, or u for user
    public static void AddUser(String accountType, String newName, String newPass) {
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
        }
    }
    
    public static boolean login(String username, String password) {
        String[][] userInfo = UserInfo();
        //System.out.println("User" + userInfo[0][1]);
        int row = userInfo.length;
        int count = 0;
        for (int i = 1; i < row; i++) {
            // this pulls the account types
            String accountType = userInfo[i][0];
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
