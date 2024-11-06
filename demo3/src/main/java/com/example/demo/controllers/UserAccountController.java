package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.MyEmailValidator;
import com.example.demo.MyPasswordValidator;
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

    @SuppressWarnings("rawtypes")

    @GetMapping("/create/{userName}/{userPass}/{userEmail}")
    public ResponseEntity createUser (@PathVariable String userName, @PathVariable String userPass, @PathVariable String userEmail) {

        if(userService.findByName(userName).isEmpty() && userService.findByEmail(userEmail).isEmpty()){
            if (MyEmailValidator.Validate(userEmail)) {
                
                if (MyPasswordValidator.Validate(userPass)) {
                    userService.createNewUser(userName, userPass, userEmail);
                    msg = "User criado com sucesso";
                    return ResponseEntity.ok(msg);
                }
                msg = "Sua senha não atinge as normas de segurança, atenção aos requisitos: Possuir ao menos 8 caracteres; Ter letras maiusculas; Ter letras minusculas; Ter números ";
                return ResponseEntity.badRequest().body(msg);
            }

            msg = "Seu email não passou na verificação! Seu email deve ter mais de 4 caracteres e possuir @ e .com ";

            return ResponseEntity.badRequest().body(msg);
        }

        msg = "O usuário já existe no banco de dados!";
        return ResponseEntity.badRequest().body(msg);

    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/delete") 
    public ResponseEntity<String> delete() { // retorno dentro do ok do responseentity
        userService.deleteAll();
        msg = "usuários deletados com sucesso";
        return ResponseEntity.ok(msg);
    }
    //http://localhost:8080/user/updatePass/nini/123@@123Ni/123/123

    // [{"id":7,"userNameCol":"nini","passWordCol":"123@@123Ni","emailCol":"ni@email.com"}]
    @GetMapping("/updatePass/{userName}/{oldPass}/{newPass}/{repPass}")
    public ResponseEntity<String> updatePassword(@PathVariable String userName, @PathVariable String newPass, @PathVariable String oldPass, @PathVariable String repPass) {
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
