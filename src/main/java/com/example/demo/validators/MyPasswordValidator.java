package com.example.demo.validators;

public class MyPasswordValidator {
    public static boolean Validate(String pass) 
    {

        if(pass.length() < 8) {

            System.out.println("\n\n\n\n\n não pasou small pass " + pass);
            return false;
        }

        // ver se uma string contem númerosz
        if(!pass.matches(".*\\d.*")) {
            System.out.println("\n\n\n\n\n não pasou sem numero " + pass +"\n\n\n");
            return false;
        }

        // ver se tem letras
        if (!pass.matches(".*[a-zA-Z].*")) {
            System.out.println("\n\n\n\n\n não passou sem letras: " + pass + "\n\n\n");
            return false;
        }
        
        boolean hasUpper = pass.matches(".*[A-Z].*");
        boolean hasLower = pass.matches(".*[a-z].*");
        
        // ver se tem letras maiusculas e minusculas
        if (hasUpper == false || hasLower == false) {
            System.out.println("\n\n\n\n\n não passou, precisa ter tanto maiúsculas quanto minúsculas: " + pass + "\n\n\n");
            return false;
        }

        if(!pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            System.out.println("\n\n\n\n\n não pasou sem caracteres especiais " + pass +"\n\n\n");
            return false;
        }

        return true;
    }
}