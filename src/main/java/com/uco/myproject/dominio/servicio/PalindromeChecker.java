package com.uco.myproject.dominio.servicio;

public class PalindromeChecker {

    private PalindromeChecker() {}

    public static boolean validate(String text){
        StringBuilder stringBuilder=new StringBuilder(text);
        stringBuilder.reverse();
        String reverseText = stringBuilder.toString();
        if(text.equals(reverseText)){
            return true;
        }else{
            return false;
        }
    }
}
