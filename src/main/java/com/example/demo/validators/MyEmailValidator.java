package com.example.demo.validators;

public class MyEmailValidator {

    public static boolean Validate(String email) 
    {

        if(email.length() < 4) {

            System.out.println("\n\n\n\n\n não pasou small email " + email);
            return false;
        }

        if(!email.contains("@") || !email.endsWith(".com")) {
            System.out.println("\n\n\n\n\n não pasou não tem @ nem .com " + email +"\n\n\n");
            return false;
        }

        return true;
    }
    
}
