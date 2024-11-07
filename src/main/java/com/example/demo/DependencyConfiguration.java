// package com.example.demo;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Scope;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.demo.dto.Token;
// import com.example.demo.services.JWTService;
// import com.example.demo.services.LoginService;
// import com.example.demo.services.UserService;
// import com.example.demo.services.impl.DefaultJWTService;
// import com.example.demo.services.impl.LoginServiceImpl;
// import com.example.demo.services.impl.UserServiceImpl;

// @Configuration
// public class DependencyConfiguration {

//     @Bean
//     @Scope("singleton") // request, prototype, singleton(se nn colocar nd é o padrão) (, oq cada um deveria ser, session
//     public UserService userService () {
//         return new UserServiceImpl();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(8);
//     }

//     @Bean
//     public JWTService<Token> jwtService() {
//         return new DefaultJWTService();
//     } 
//     @Bean
//     public LoginService loginService () {
//         return new LoginServiceImpl();
//     }
// }

