package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.impl.UserSeriveImpl;
import com.example.demo.services.UserService;

@Configuration
public class DependencyConfiguration {

    // O CONTROLLER DEVE DEPENDER DA INTERFACE DO SERVIÇO,
    
    @Bean
    @Scope("singleton") // request, prototype, singleton(se nn colocar nd é o padrão) (, oq cada um deveria ser, session
    public UserService userService () {
        return new UserSeriveImpl();
    }

}

// controlador dependencia nas interfaces , nn nas classes, sempre interface
