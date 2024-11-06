package com.example.demo.services;

import com.example.demo.models.User;

public interface UserAuth {
    User loginByUsername(String username);
    User loginByEmail(String email);
}