package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**Represents the Controller for the TextEditor View. 
 *
 * @author lamju
 */
import DataAccessLayer.Repository;
import ModelClass.User;
import Views.LoginScreen;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;

public class TextEditorController {
    // Obtain a suitable logger.
    private static Logger logger = Logger.getLogger(LoginScreen.class.getName()); //getLogger() method 
    //names the logger, however this value may be a gibberish string if you wished. 
    
    
    /** Creates a new text file saved to the specified file path.  
     * 
     * @param text
     * @param filePath 
     */
   
    Repository repo = new Repository(); 
    
    public TextEditorController () {
        try {
            //The logger requires at least one Handler to know where/how to print the message.

            //Setting up FileHandler
            FileHandler myFileHandler = new FileHandler("controllerErrLog.txt");
            myFileHandler.setLevel(Level.ALL); //Defines the minimum log level that this handler accepts

            logger.addHandler(myFileHandler);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error creating File Handler", ex.getMessage()); 
        } catch (SecurityException ex) {
            logger.log(Level.WARNING, "SecurityException!", ex.getMessage()); 
        }
    }
    
    public void returnAllUsers() {
        repo.returnAllUsers();
    }
    
    public void saveToTextFile(String text, String filePath) {
    PrintWriter output;
    
        try {
            output = new PrintWriter(filePath);
            output.println(text);
            output.close(); 
            
            System.out.println("Successfully saved");
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, "Error saving to text file", ex.getMessage()); 
        }
    }
    
    /** Returns the text content of the specified file path/text file.  
     * 
     * @param filePath
     * @return 
     */
    public String getFileText(String filePath) {
        String fileText = ""; 
        
        File myFile = new File(filePath);
        Scanner myReader; 
        
        try {
            myReader = new Scanner(myFile);
            
            while(myReader.hasNext()) {
                fileText+= myReader.nextLine() + "\n"; 
            }
            
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, "Error getting text from text file", ex.getMessage()); 
        }
        
        return fileText; 
    }
    
    ///////////////////////////////////////////////////////////
        //SUMMARY: This method returns boolean true if username already exists. 
    public boolean isExistingUsername(String username) {
        boolean isExistingUser = false; 
        
        List<User> retrievedUsersList = new LinkedList<User>(); 
        retrievedUsersList = repo.returnAllUsers(); 
                
        User aUser = null; 
        for(var user : retrievedUsersList) {
            aUser = (User)user; 
            
            if(aUser.username.equals(username)) {
                isExistingUser = true; 
            }
        }
        
        return isExistingUser; 
    }
    
        //SUMMARY: This method checks whether the login details are correct
    public boolean isCorrectLoginDetails(String username, String password) {
        boolean isExistingLogin = false; 
        
        //Retrieve all existing user objects from the db.  
        List<User> retrievedUsersList = new LinkedList<User>();  
        retrievedUsersList = repo.returnAllUsers(); 
        
        User aUser = null; 
        //For each object in the array, check if there is a match for the user inputs and the object properties. 
        for(var user : retrievedUsersList) {
            aUser = (User)user; 
            
            if(aUser.username.equals(username) && aUser.password.equals(password)) {
                isExistingLogin = true; 
            }
        }
        return isExistingLogin; 
    }
    
        public Boolean saveNewUser(User newUser) {
        if(repo.saveUserToDB(newUser)) {
            return true; 
        }
        else {
            return false; 
        }
    }
}
