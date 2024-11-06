package com.example.demo.services;

import java.util.List;

import com.example.demo.models.UserModel;

public interface UserService {

    void createNewUser(String userName, String pass, String email);
    List<UserModel> getAllUsers();
    List<UserModel> findByName(String name);
    List<UserModel> findByEmail(String email);
    void deleteAll();
    void updatePassByUserName(String userName, String newPass);
}
