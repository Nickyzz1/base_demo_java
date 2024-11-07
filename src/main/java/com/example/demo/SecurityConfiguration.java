package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.dto.Token;
import com.example.demo.filters.JWTAuthenticationFilter;
import com.example.demo.services.JWTService;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    JWTService<Token> jwtService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // HttpSecurity: Aqui você configura como as requisições HTTP serão tratadas em termos de segurança. No seu caso, está desabilitando a proteção CSRF (csrf().disable()), permitindo o acesso sem autenticação para certos endpoints (permitAll()), e exigindo autenticação para outros (anyRequest().authenticated()).
        // como mandar jwt para header depois do login?
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user").permitAll() // esses end poits permiete que façam as ações semprecisar fazer login, vc escolhe quais end poinst nn precisam de autenticação
                // .requestMatchers("/**").permitAll()
                .requestMatchers("/login").permitAll() // quando testar colocar no permit all
                .requestMatchers("/user/create").permitAll()
                .requestMatchers("/user/deleteAll").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JWTAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class) // tds os end points vão chamar isso, ele manda para essa classe
            .build(); // addFilterBefore(...): Esse método insere o filtro JWTAuthenticationFilter na cadeia de filtros do Spring Security. O JWTAuthenticationFilter será executado antes do filtro padrão de autenticação baseado em nome de usuário e senha (UsernamePasswordAuthenticationFilter). O filtro JWT vai verificar se o token JWT enviado na requisição é válido e, se for, autentica o usuário automaticamente.
    }
}