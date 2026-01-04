package com.beksultan.vetclinic;

public class Validating {
    public static boolean isNumber(String input){
        char[] chars = input.toCharArray();

        for(int i = 0; i < chars.length; i++){
            char c = chars[i];

            if(i == 0 && c == '+'){
                continue;
            }

            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidStr(String input){
        if(input != null && !input.isBlank()){
            return true;
        }
        return false;
    }
}