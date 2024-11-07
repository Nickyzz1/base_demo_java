package com.example.demo.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.services.PasswordEncoderService;

@Service
public class BcryptPasswordEncoderService implements PasswordEncoderService {
    @Override
    public String encode(String password)
    {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
