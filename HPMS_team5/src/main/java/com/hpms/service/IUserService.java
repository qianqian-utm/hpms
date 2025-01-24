package com.hpms.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hpms.model.User;

public interface IUserService extends UserDetailsService {
    void addUser(User user);
    List<User> getUserList();
    void updateUser(User updatedUser);
    void deleteUser(int userId);
    User getUserByEmailAndPassword(String email, String password);
    User getUserById(int userId);
    List<User> getPatients();
    List<User> getDoctors();
    User getUserByEmail(String email);
    boolean existsByEmail(String email);
    void save(User user);
}