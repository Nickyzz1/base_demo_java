package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserCreationRequest;
import com.example.demo.dto.updatePassDto;
import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import com.example.demo.validators.MyEmailValidator;
import com.example.demo.validators.MyPasswordValidator;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    private final UserService userService;

    @Autowired
    public UserAccountController(UserService userService) {
        this.userService = userService;
    }

    String msg = "Default";

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserCreationRequest request) {
        String userName = request.getUserName();
        String userPass = request.getUserPass();
        String userEmail = request.getUserEmail();

        if (userService.findByName(userName).isEmpty() && userService.findByEmail(userEmail).isEmpty()) {
            if (MyEmailValidator.Validate(userEmail)) {
                if (MyPasswordValidator.Validate(userPass)) {
                    userService.createNewUser(userName, userPass, userEmail);
                    msg = "User criado com sucesso";
                    return ResponseEntity.ok(msg);
                }
                msg = "Sua senha não atinge as normas de segurança, atenção aos requisitos: Possuir ao menos 8 caracteres; Ter letras maiúsculas; Ter letras minúsculas; Ter números ";
                return ResponseEntity.badRequest().body(msg);
            }
            msg = "Seu email não passou na verificação! Seu email deve ter mais de 4 caracteres e possuir @ e .com ";
            return ResponseEntity.badRequest().body(msg);
        }

        msg = "O usuário já existe no banco de dados!";
        return ResponseEntity.badRequest().body(msg);
    }
   
    @PatchMapping("/updatePass") // mudar no postman o método, deve estar em body e raw json para fazer post e patch
    public ResponseEntity<String> updatePassword(@RequestBody updatePassDto request) {

        String userName = request.getUserName();
        String newPass = request.getNewPass();
        String oldPass = request.getOldPass();
        String repPass = request.getRepPass();

        if (!userService.findByName(userName).isEmpty()) {

            List<UserModel> currUser = userService.findByName(userName);

            System.out.println("achou o user");

            if (currUser.get(0).getPassWordCol().equals(oldPass))                                                 
            {
                System.out.println("achou o password");

                if (newPass.equals(repPass)) {
                    if (MyPasswordValidator.Validate(newPass)) {

                        userService.updatePassByUserName(userName, newPass);
                        msg = "Senha atualizada com sucesso!";
                        System.out.println("atualizou");
                        return ResponseEntity.ok(msg);
                    }
                    msg = "A nova senha precisa atingir os requistos: Possuir ao menos 8 caracteres; Ter letras maiusculas; Ter letras minusculas; Ter números";
                    return ResponseEntity.ok(msg);

                }
                msg = "As senhas e a repetição de senha devem ser iguais";
                return ResponseEntity.badRequest().body(msg);
            }

            msg = "As senha atual está incorreta para o usuário";
            return ResponseEntity.badRequest().body(msg);
        }
        msg = "O usuário não existe no banco";
        return ResponseEntity.badRequest().body(msg);
    }

    // Funcões experimentais para testar GETS, não devem ser condideradas poris atribuem mais coisa do que esse controlador deveria ter

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAll();
        msg = "Usuários deletados com sucesso";
        return ResponseEntity.ok(msg);
    }
}

// @GetMapping("/create/{userName}/{userPass}/{userEmail}")
// public ResponseEntity createUser (@PathVariable String userName,
// @PathVariable String userPass, @PathVariable String userEmail) {

// if(userService.findByName(userName).isEmpty() &&
// userService.findByEmail(userEmail).isEmpty()){
// if (MyEmailValidator.Validate(userEmail)) {

// if (MyPasswordValidator.Validate(userPass)) {
// userService.createNewUser(userName, userPass, userEmail);
// msg = "User criado com sucesso";
// return ResponseEntity.ok(msg);
// }
// msg = "Sua senha não atinge as normas de segurança, atenção aos requisitos:
// Possuir ao menos 8 caracteres; Ter letras maiusculas; Ter letras minusculas;
// Ter números ";
// return ResponseEntity.badRequest().body(msg);
// }

// msg = "Seu email não passou na verificação! Seu email deve ter mais de 4
// caracteres e possuir @ e .com ";

// return ResponseEntity.badRequest().body(msg);
// }

// msg = "O usuário já existe no banco de dados!";
// return ResponseEntity.badRequest().body(msg);

// }
