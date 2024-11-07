package com.example.demo.services;

import java.util.List;

import com.example.demo.models.UserModel;

public interface LoginService {
    List<UserModel> login(String login);
}