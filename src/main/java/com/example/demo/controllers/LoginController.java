package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.models.UserModel;
import com.example.demo.services.JWTService;
import com.example.demo.services.LoginService;
import com.example.demo.dto.Token;


@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
   
    @Autowired
    JWTService<Token> jwtService;

    @Autowired
    public LoginController(LoginService logService) {
        this.loginService = logService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserLoginDto request) {
        String login = request.getLogin();
        String pass = request.getPass();

        java.util.List<UserModel> users = loginService.login(login);
        
        if (!users.isEmpty()) {
            UserModel user = users.get(0);
            if (encoder.matches(pass, user.getPassWordCol())) {
                
                String msg = "Login realizado com sucesso";
                Token token = new Token();
                token.setId(user.getId());
                token.setRole(user.getEmailCol());
        
                // Aq é feito o JWT
                var jwt = jwtService.get(token);
                LoginResponseDto response = new LoginResponseDto(msg, jwt);
                return ResponseEntity.ok(response);
            }
            String msg = "Senha incorreta";
            return ResponseEntity.badRequest().body(new LoginResponseDto(msg, null));
        }

        String msg = "O usuário não existe no banco de dados!";
        return ResponseEntity.badRequest().body(new LoginResponseDto(msg, null));
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
