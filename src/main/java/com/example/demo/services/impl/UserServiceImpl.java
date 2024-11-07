package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepositoy;
import com.example.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoy userRepository;

    @Override
    public void createNewUser(String userName, String pass, String email) {
        var coisado = encode(pass);
        userRepository.save(new UserModel(userName, coisado, email));
    }

    @Override
    public List<UserModel> getAllUsers() {
       return userRepository.findAll();
    }

    @Override
    public List<UserModel> findByName(String name) {
        return userRepository.findByUserNameCol(name);
    }

    @Override
    public List<UserModel> findByEmail(String email) {
        return userRepository.findByEmailCol(email);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void updatePassByUserName(String userName, String newPass) {
        userRepository.updatePassWordColByUserName(userName, newPass);
    }

    @Override
    public String encode(String password) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
