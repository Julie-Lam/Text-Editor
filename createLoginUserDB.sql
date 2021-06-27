CREATE DATABASE loginUserDB; 

CREATE TABLE User (
username varchar(255) NOT NULL PRIMARY KEY, 
password varchar(255) NOT NULL, 
firstName varchar(255) NOT NULL, 
lastName varchar(255) NOT NULL, 
dateOfBirth date NOT NULL, 
userType varchar(255) NOT NULL
);

INSERT INTO User(username, password, firstName, lastName, dateOfBirth, userType)
VALUES ('lam.julie@hotmail.com', '1234', 'Julie', 'Lam', '1995-01-25', 'Basic'); 
 
SELECT * FROM User;