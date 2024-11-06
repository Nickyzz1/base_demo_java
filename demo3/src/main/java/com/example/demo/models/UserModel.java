package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "userName")
    private String userNameCol; // é com esses nomes que se faz as consultas ao reposit´toio
    @Column(name = "userPass")
    private String passWordCol; // esse é o nome que eu devo colocar quando eu criar um métood no repositório
    @Column(name = "userEmail")
    private String emailCol; // esse nome nn pode começare com maiúsculo

    public UserModel () {}

    public UserModel(String userName, String userPass, String userEmail) {
        this.userNameCol = userName;
        this.passWordCol = userPass;
        this.emailCol = userEmail;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserNameCol() {
        return userNameCol;
    }
    public void setUserNameCol(String userName) {
        this.userNameCol = userName;
    }
    public String getPassWordCol() {
        return passWordCol;
    }
    public void setPassWordCol(String passWord) {
        this.passWordCol = passWord;
    }
    public String getEmailCol() {
        return emailCol;
    }
    public void setEmailCol(String email) {
        this.emailCol = email;
    }

}
