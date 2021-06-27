/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julie
 */
public class User {
    public String username; 
    public String password; 
    public String firstName; 
    public String lastName; 
    public Date dateOfBirth; 
    public String userType; 
    
    
    private static final String FilePath = "Part2_FileOutIn/login.txt"; 

    public User(String username, String password, String firstName, String lastName, Date dateOfBirth, String userType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType; 
    }
    
    public User(String username, String password) {
        this.username = username; 
        this.password = password; 
    }
    
}
