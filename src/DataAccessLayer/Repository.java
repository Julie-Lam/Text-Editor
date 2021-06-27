/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import ModelClass.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author lamju
 */
public class Repository {
    //TODO: Create LOGGER FILE IN EXCEPTIONS 
    // Obtain a suitable logger.
    private static final Logger logger = Logger.getLogger(Repository.class.getName()); //getLogger() method 
    //names the logger, however this value may be a gibberish string if you wished. 
    
    private Connection conn;
    private ResultSet rs;
    private Statement stmt;
    private String url, uName="root";
    
    public Repository() {
        try {
            //Setting up FileHandler
            FileHandler myFileHandler = new FileHandler("repoErrorLog.txt");
            myFileHandler.setLevel(Level.ALL); //Defines the minimum log level that this handler accepts
            
            logger.addHandler(myFileHandler);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "An IOException occured.", ex.getMessage()); 
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "A SecurityException occured.", ex.getMessage()); 
        }
    }
    private Connection connectToDb() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");            
            url = "jdbc:mysql://localhost:3306/loginuserdb";
            conn = DriverManager.getConnection(url,uName,"root");   
            

        }
        catch(Exception e)
        {
            logger.log(Level.WARNING, "Failed to connect to DB", e.getMessage());
            System.out.println(e);
        }
         return conn;  
    }
    
    public ResultSet returnResultSet() {
        try {
            stmt = connectToDb().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("select * from User;");
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Failed to retrieve Users ResultSet", ex.getMessage()); 
        }
        
        return rs; 
    }
    
    public List<User> returnAllUsers() {
        String userName; 
        String password; 
        String firstName; 
        String lastName; 
        Date dateOfBirth; 
        String userType; 

        User aUser; 
        List<User> userList = new LinkedList<User>(); 
        
        try
        {
            rs = returnResultSet();

            //Retrieves a list of users from db 
            while (rs.next())
            {
                userName = rs.getString("userName"); 
                password = rs.getString("password"); 
                firstName = rs.getString("firstName"); 
                lastName = rs.getString("lastName"); 
                dateOfBirth = rs.getDate("dateOfBirth"); 
                userType = rs.getString("userType"); 

                aUser = new User(userName, password, firstName, lastName, dateOfBirth, userType); 
                
                userList.add(aUser); 
            }
            
            rs.close();
            conn.close();  
        }
        catch(Exception e)
        {
            logger.log(Level.WARNING, "Error returning list of user objects", e.getMessage());
        }
        
        return userList; 
    }
    
    public Boolean saveUserToDB(User newUser){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateOfBirth = dateFormat.format(newUser.dateOfBirth);   
        
        try {
            User aUser = null;
            stmt = connectToDb().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("INSERT INTO user ()"
                    + "VALUES ('" + newUser.username + "', '" + newUser.password + "', '" + newUser.firstName
                    + "', '" + newUser.lastName +"', '" + formattedDateOfBirth + "', '" + newUser.userType + "') ");
            return true; 
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error in executing SQL statement to update DB", ex.getMessage());
            return false; 
        }
    }
}
