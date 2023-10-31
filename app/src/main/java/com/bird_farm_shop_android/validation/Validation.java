package com.bird_farm_shop_android.validation;

public class Validation {
    public static boolean checkContainsNumber(String input)
    {
        for(Character ch : input.toCharArray())
        {
            if(Character.isAlphabetic(ch)) return false;
        }
        return true;
    }

    public static boolean checkContainsString(String input)
    {
        for(Character ch : input.toCharArray())
        {
            if(Character.isDigit(ch)) return false;
        }
        return true;
    }
}
