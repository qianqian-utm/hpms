package com.hpms.service;

import com.hpms.model.User; 
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService {
    private static ArrayList<User> userList = new ArrayList<>();
    
    public UserService() {
        if (userList.isEmpty()) {
            prepareDummyUserData();
        }
    }

    public ArrayList<User> prepareDummyUserData() {
        userList.add(new User("Elizabeth", "Polson", "elsabethpolsan@hotmail.com", "+91 12345 67890", 1, 2));
        userList.add(new User("John", "David", "davidjohn22@gmail.com", "+91 12345 67890", 1, 1));
        userList.add(new User("Krishtav", "Rajan", "krishnarajan23@gmail.com", "+91 12345 67890", 2, 1));
        userList.add(new User("Sumanth", "Tinson", "tintintin@gmail.com", "+91 12345 67890", 2, 2));
        userList.add(new User("EG", "Subramani", "egs31322@gmail.com", "+91 12345 67890", 2, 2));
        userList.add(new User("Ranjan", "Maari", "ranajanmaari@yahoo.com", "+91 12345 67890", 2, 1));
        userList.add(new User("Philipile", "Gopal", "gopal22@gmail.com", "+91 12345 67890", 2, 1));
        
        return userList;
    }
    
    public ArrayList<User> addUser(User newUser) {
        userList.add(newUser);
        return userList;
    }
    
    public ArrayList<User> getUserList() {
        return userList;
    }

    public void updateUser(User updatedUser) {
        // Find and update the user in the list
        for (int i = 0; i < userList.size(); i++) {
            User existingUser = userList.get(i);
            // Currently use email to identify user
            if (existingUser.getEmail().equals(updatedUser.getEmail())) {
                userList.set(i, updatedUser);
                break;
            }
        }
    }
}