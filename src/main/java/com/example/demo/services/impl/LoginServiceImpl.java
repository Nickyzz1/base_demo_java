package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepositoy;
import com.example.demo.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepositoy userRepository;

    @Override
    public List<UserModel> login(String login) {
        return userRepository.findByUserNameColAndEmailCol(login);
    }
}
