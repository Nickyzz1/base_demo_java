package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepositoy extends JpaRepository<UserModel, Long> {

    List<UserModel> findByUserNameCol(String name);
    List<UserModel> findByEmailCol(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.passWordCol = :newPass WHERE u.userNameCol = :userName")
    // @Query("UPDATE UserModel u SET u.passWordCol = :newPass WHERE u.userNameCol = :userName or u.emailCol = :userName")
    void updatePassWordColByUserName(@Param("userName") String userName, @Param("newPass") String newPass); // o nome do parâmetro precisa ser o mesmo nome da query
    // query personlizada
    @Query("SELECT u FROM UserModel u WHERE u.userNameCol = :loginValue OR u.emailCol = :loginValue")
    List<UserModel> findByUserNameColAndEmailCol(@Param("loginValue") String loginValue);

}
