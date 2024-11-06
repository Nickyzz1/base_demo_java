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

import com.example.demo.MyEmailValidator;
import com.example.demo.MyPasswordValidator;
import com.example.demo.dto.UserCreationRequest;
import com.example.demo.dto.updatePassDto;
import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;

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


    // @GetMapping("/create/{userName}/{userPass}/{userEmail}")
    // public ResponseEntity createUser (@PathVariable String userName, @PathVariable String userPass, @PathVariable String userEmail) {

    //     if(userService.findByName(userName).isEmpty() && userService.findByEmail(userEmail).isEmpty()){
    //         if (MyEmailValidator.Validate(userEmail)) {
                
    //             if (MyPasswordValidator.Validate(userPass)) {
    //                 userService.createNewUser(userName, userPass, userEmail);
    //                 msg = "User criado com sucesso";
    //                 return ResponseEntity.ok(msg);
    //             }
    //             msg = "Sua senha não atinge as normas de segurança, atenção aos requisitos: Possuir ao menos 8 caracteres; Ter letras maiusculas; Ter letras minusculas; Ter números ";
    //             return ResponseEntity.badRequest().body(msg);
    //         }

    //         msg = "Seu email não passou na verificação! Seu email deve ter mais de 4 caracteres e possuir @ e .com ";

    //         return ResponseEntity.badRequest().body(msg);
    //     }

    //     msg = "O usuário já existe no banco de dados!";
    //     return ResponseEntity.badRequest().body(msg);

    //}

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
    //http://localhost:8080/user/updatePass/nini/123@@123Ni/123/123

    // [{"id":7,"userNameCol":"nini","passWordCol":"123@@123Ni","emailCol":"ni@email.com"}]
    @PatchMapping("/updatePass") // mudasr mo postman o método, deve estra em body e raw json para fazer post e patch
    public ResponseEntity<String> updatePassword(@RequestBody updatePassDto request) {

        String userName = request.getUserName();
        String newPass = request.getNewPass();
        String oldPass = request.getOldPass();
        String repPass =  request.getRepPass();


        if(!userService.findByName(userName).isEmpty()){

            List<UserModel> currUser = userService.findByName(userName);

            System.out.println("achou o user");

            if(currUser.get(0).getPassWordCol().equals(oldPass)) // pq ele retorna uma lista de usuários, e como pode haver mais que um deve se específicar que é o primeiro
            {
                System.out.println("achou o password");

                if (newPass.equals(repPass)) { 
                    if(MyPasswordValidator.Validate(newPass)) {

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
}
