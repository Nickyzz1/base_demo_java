package com.example.demo.controllers;

import com.example.demo.dto.ProductData;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;

// tolken errado eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoibmlAZW1haWwuY29tIiwiaWQiOjIsImlhdCI6MTczMTAxNzgzMywiZXhwIjoxNzMxMDIxNDMzfQ.Yveoyqyo2QB3smGGf2I70tsz2sKZ--HqyW7_NHewChA
// tolken certo eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoibmlAbG9qYS5jb20iLCJpZCI6MywiaWF0IjoxNzMxMDE3OTQwLCJleHAiOjE3MzEwMjE1NDB9.nKE65_rC0_6-65JV4DBeLF23eMOEQTbKabwmBK7CgQE

@RestController
public class ProductCadController {

    @Autowired
    ProductRepository prodRepo;

    @SuppressWarnings("rawtypes")
    @Autowired
    JWTService jwtService;

    @PostMapping("/product")
    public ResponseEntity<String> cadNewProduct(@RequestHeader("Authorization") String authorization, @RequestBody ProductData data) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>("Token não enviado ou no formato incorreto", HttpStatus.UNAUTHORIZED);
        }

        String token = authorization.substring(7);  // Remover "Bearer " do token

        // Valida o token
        Token userToken = (Token) jwtService.validate(token);
        if (userToken == null) {
            return new ResponseEntity<>("Token inválido ou expirado", HttpStatus.UNAUTHORIZED);
        }

        // Verifica o domínio do email
        String email = userToken.getRole();
        Integer position = email.indexOf("@");
        if (position == -1 || !email.substring(position).equals("@loja.com")) {
            return new ResponseEntity<>("Usuário sem permissão!", HttpStatus.FORBIDDEN);
        }

        // Criação do novo produto
        Product newProduct = new Product();
        newProduct.setTitulo(data.title());
        newProduct.setValor(data.value());

        prodRepo.save(newProduct);

        return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.OK);
    }

}
