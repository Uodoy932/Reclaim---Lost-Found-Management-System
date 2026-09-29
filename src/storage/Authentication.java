package storage;

import exceptions.InvalidLoginException;
import model.User;
import java.util.ArrayList;




public class Authentication {

    private ArrayList<User> users;
    private FileManager fileManager;

    // Constructor
    public Authentication(FileManager fileManager) {
        this.fileManager = fileManager;
        this.users = (ArrayList<User>) fileManager.loadUsers();
    }

    
    //CheckDuplicateID
    public boolean isUserIdTaken(String userId) 
    {

        for (User user : users)
        {
            if (user.getUserId().equalsIgnoreCase(userId)){
            	return true;
            }
                
        }

        return false;
    }

    
    // Register USER
    public User register(String userId, String name, String email,String phoneNumber, String password)throws InvalidLoginException {

    
        if (userId == null || userId.trim().isEmpty()){
            throw new InvalidLoginException("Student ID can not be empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new InvalidLoginException("Password can not be empty.");
        }

        
        if (isUserIdTaken(userId)) {
            throw new InvalidLoginException("This Student ID is already registered.");
        }

        
        User newUser = new User(userId,name,email,phoneNumber,password);
        users.add(newUser);//ArrayList
        fileManager.appendUser(newUser);//File
        return newUser;
    }

    
    
    // Login
    public User login(String userId, String password)throws InvalidLoginException{

        for (User user : users) 
        {
        	
            if (user.getUserId().equalsIgnoreCase(userId)) 
            {
                if (user.checkPassword(password)) {return user;}
                throw new InvalidLoginException("Incorrect password.");
            }
            
        }

        throw new InvalidLoginException("Student ID not found.");
    }


   
    
}