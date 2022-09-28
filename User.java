
/**
 * File: User.java
 * @author Group 4(Andrew J. Martino, Kyle Pope, Rob Miller)
 * Create Date: 9/27/2022
 * Purpose: SubClass of the user class to get user information
 * 
 * Revision History:
 * Andrew - Add the method from the Pseudocode and implement password check
 * 
 */
import static java.lang.System.exit;
public class User {
    
    private String userName;
    private String password;
    private UserType typeUser;
            
  
    public void getUserType(){
       
    }
    
    public String login(String userName, String password){
        return null;
    }
    
    public void logout(){
         exit(0);
    }
    
    public String changeUserName(){
        return userName;
    }
    
    public String changePassword(String password){
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
    }
}
